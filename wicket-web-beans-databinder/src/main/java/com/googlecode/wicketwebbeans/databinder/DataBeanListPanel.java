/*---
   Copyright 2007 The Scripps Research Institute
   http://www.scripps.edu

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
   
        http://www.apache.org/licenses/LICENSE-2.0
   
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
---*/

package com.googlecode.wicketwebbeans.databinder;

import com.googlecode.wicketwebbeans.containers.BeanTablePanel;
import com.googlecode.wicketwebbeans.model.BeanMetaData;
import net.databinder.hib.Databinder;
import net.databinder.components.hib.SearchPanel;
import net.databinder.models.hib.HibernateProvider;
import net.databinder.models.hib.CriteriaBuilder;
import net.databinder.models.hib.CriteriaSorter;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.hibernate.classic.Session;

/**
 * A basic Databinder/Hibernate Bean List panel. 
 * A subclass and corresponding beanprops file are required for customization.
 * 
 * @author Mark Southern (mrsouthern)
 */
public abstract class DataBeanListPanel extends Panel
{
	private BeanTablePanel panel;
	private BeanMetaData metaData;
	private Class<?> beanClass;
	private CriteriaBuilder criteriaBuilder;
	private BeanMetaData beanMetaData;
	private int numRows;

	public DataBeanListPanel(String id, Class<?> beanClass)
	{
		this(id, beanClass, null);
	}

	public DataBeanListPanel(String id, String beanClassName) throws ClassNotFoundException
	{
		this(id, beanClassName, null);
	}

	public DataBeanListPanel(String id, String beanClassName, CriteriaBuilder criteriaBuilder) throws ClassNotFoundException
	{
		this(id, Class.forName(beanClassName), criteriaBuilder);
	}
    
	public DataBeanListPanel(String id, Class<?> beanClass, CriteriaBuilder criteriaBuilder)
	{
		this(id, beanClass, criteriaBuilder, null);
	}

	public DataBeanListPanel(String id, Class<?> beanClass, CriteriaBuilder criteriaBuilder, BeanMetaData beanMetaData)
	{
		this(id, beanClass, criteriaBuilder, null, 20);
	}

	/**
	 *
	 * @param id
	 * @param beanClass the fully qualified class name of the bean to be edited
	 * @param criteriaBuilder
	 * @param beanMetaData
	 * @param numRows
	 */
	public DataBeanListPanel(String id, Class<?> beanClass, CriteriaBuilder criteriaBuilder, BeanMetaData beanMetaData, int numRows)
	{
		super(id);
		this.beanClass = beanClass;
		this.criteriaBuilder = criteriaBuilder;
		this.beanMetaData = beanMetaData;
		this.numRows = numRows;
	}

	@Override
	public void onBeforeRender()
	{
		if (panel == null) {
			int rows = (numRows < 1) ? 20 : numRows;

			getHibernateSession().beginTransaction();
			//metaData = new BeanMetaData(beanClass, null, this, null, true);
			metaData = beanMetaData != null ? beanMetaData :
					new BeanMetaData(beanClass, null, this, null, true);
			Label label = new Label("label", new Model<String>(metaData.getParameter("label")));
			add(label);

			SearchPanel search = newSearchPanel("search", new Model<String>(null));
			add(search);

			String orderBy = metaData.getParameter("orderBy");
			boolean asc = true;
			if (orderBy != null && orderBy.contains(" ")) {
				String[] items = orderBy.split("\\s+");
				orderBy = items[0];
				asc = ("desc".equalsIgnoreCase(items[1]) ? false : true);
			}

			String[] filters = metaData.getParameterValues("filter");
			final DataSearchFilter filter = new DataSearchFilter(search, filters);
			if (criteriaBuilder != null) {
				filter.addCriteriaBuilder(criteriaBuilder);
			}
			CriteriaSorter sorter = new CriteriaSorter(orderBy, asc);

			IDataProvider<Void> provider = new HibernateProvider<Void>(beanClass, filter, sorter);
			panel = new BeanTablePanel("beanTable", provider, sorter, metaData, true, rows);
			panel.setOutputMarkupId(true);
			Form<Void> form = new Form<Void>("form");
			add(form);
			form.add(panel);
		}
		super.onBeforeRender(); // mandatory if overriding
	}

	/**
	 * 
	 * @param target
	 * @param form
	 * @param bean
	 */
	public void edit(AjaxRequestTarget target, Form<?> form, Object bean)
	{

	}

	/**
	 * 
	 * @param target
	 * @param form
	 * @param bean
	 */
	public void delete(AjaxRequestTarget target, Form<?> form, Object bean)
	{
		// confirm message shown by wwb. If we get here we do a delete
		Session session = getHibernateSession();
		session.beginTransaction();
		session.delete(bean);
		session.getTransaction().commit();
		if (target != null)
		{// ajax request
		    target.addComponent(panel);
		}
	}
    
	/**
	 * Gets the table panel to be refreshed on a search.
	 * @return
	 */
	protected Component getTablePanel()
	{
		return panel;
	}

	/**
	 * Creates instance of new search panel, override to supply your own search panel
	 *
	 * @param wicketId
	 * @param model
	 * @return the SearchPanel.
	 */
	protected SearchPanel newSearchPanel(String wicketId, IModel<?> model)
	{
		SearchPanel search = new SearchPanel(wicketId, model) {
			private static final long serialVersionUID = 1282855848886535520L;
			public void onUpdate(AjaxRequestTarget target)
			{
				target.addComponent(getTablePanel());
			}
		};
		search.setDefaultModel(model);
		return search;
	}

	/**
	 * Overridable method to get Hibernate Session, fixes issue 16
	 *
	 * @return By default, the Databinder Hibernate session
	 */
	protected Session getHibernateSession()
	{
		return Databinder.getHibernateSession();
	}

}
