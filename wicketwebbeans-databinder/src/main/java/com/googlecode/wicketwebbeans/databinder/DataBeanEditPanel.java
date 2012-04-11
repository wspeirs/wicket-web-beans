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

import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.model.BeanMetaData;
import net.databinder.hib.Databinder;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.hibernate.classic.Session;

/**
 * A basic Databinder/Hibernate Bean editing panel. A subclass and matching
 * beanprops file are required for customization.
 * 
 * @author Mark Southern (mrsouthern)
 * @author Vitek Rozkovec - replacement component
 */
public abstract class DataBeanEditPanel extends Panel
{
	private Page returnPage;
	private Component replacementComponent;
	private BeanForm beanForm;
	private BeanMetaData metaData;
	private Object bean;
	private BeanMetaData beanMetaData;

	/**
	 * @param id
	 *            wicket id
	 * @param bean
	 *            the Bean to be edited
	 * @param replacementComponent
	 *            component which will be switched with this panel after save
	 */
	public DataBeanEditPanel(String id, Object bean,
				Component replacementComponent)
	{
		this(id, bean);
		this.replacementComponent = replacementComponent;
	}

	/**
	 * @param id
	 *            wicket id
	 * @param bean
	 *            the Bean to be edited
	 * @param returnPage
	 *            the page to return to after saving
	 */
	public DataBeanEditPanel(String id, Object bean, Page returnPage)
	{
		this(id, bean);
		this.returnPage = returnPage;
	}

	/**
	 * @param id
	 *            wicket id
	 * @param bean
	 *            the Bean to be edited
	 * @param replacementComponent
	 *            component which will be switched with this panel after save
	 * @param beanMetaData
	 *            the BeanMetaData to use for rendering
	 */
	public DataBeanEditPanel(String id, Object bean,
			Component replacementComponent, BeanMetaData beanMetaData)
	{
		this(id, bean, beanMetaData);
		this.replacementComponent = replacementComponent;
	}

	/**
	 * @param id
	 *            wicket id
	 * @param bean
	 *            the Bean to be edited
	 * @param returnPage
	 *            the page to return to after saving
	 * @param beanMetaData
	 *            the BeanMetaData to use for rendering
	 */
	public DataBeanEditPanel(String id, Object bean, 
			Page returnPage, BeanMetaData beanMetaData)
	{
		this(id, bean, beanMetaData);
		this.returnPage = returnPage;
	}

	private DataBeanEditPanel(String id, Object bean)
	{
		this(id, bean, (BeanMetaData) null);
	}

	private DataBeanEditPanel(String id, Object bean, BeanMetaData beanMetaData)
	{
		super(id);
		this.bean = bean;
		this.beanMetaData = beanMetaData;
	}

	@Override
	public void onBeforeRender()
	{
		if (beanForm == null)
		{
			metaData = beanMetaData != null ? beanMetaData :
					new BeanMetaData(bean.getClass(), null, this, null, false);
			beanForm = new BeanForm("beanForm", bean, metaData);
			add(beanForm);
		}
		super.onBeforeRender();  // mandatory if overriding
	}

	/**
	 * 
	 * @param target
	 * @param form
	 * @param bean
	 */
	public void save(AjaxRequestTarget target, Form<?> form, Object bean)
	{
		if (!beanForm.validateRequired())
		{
		    return; // Errors
		}
		Session session = getHibernateSession();
		session.saveOrUpdate(bean);
		session.getTransaction().commit();
		if (returnPage != null)
		{
			returnPage.info("Saved.");
			setResponsePage(returnPage);
		} else
		{
			getPage().info("Saved.");
			if (replacementComponent != null)
			{
				replaceWith(replacementComponent);
                        }
		}

	}

	/**
	 * 
	 * @param target
	 * @param form
	 * @param bean
	 */
	public void cancel(AjaxRequestTarget target, Form<?> form, Object bean)
	{
		getHibernateSession().evict(bean);
		if (returnPage != null)
		{
			returnPage.info("Canceled edit.");
			setResponsePage(returnPage);
		} else
		{
			getPage().info("Canceled edit.");
			if (replacementComponent != null)
			{
				replaceWith(replacementComponent);
                        }
		}
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
