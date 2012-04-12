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

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import net.databinder.components.hib.SearchPanel;
import net.databinder.models.hib.CriteriaBuilder;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * A Databinder/Hibernate filtering component suitable for adding to a BeanTablePanel in conjunction
 * with a DatabinderProvider. It uses a Databinder Search panel to obtain the search criterion.
 * A typical use case might be:
 * 
 * SearchPanel search = new SearchPanel("search", new Model(null))
 * {
 *      public void onUpdate(AjaxRequestTarget target) {
 *          target.addComponent(getTableBeanPanel());
 *      }
 * };
 * add(search);
 * DataSorter sorter = new DataSorter(metaData.getParameter("orderBy"));
 * IDataProvider provider = new DatabinderProvider(beanClass,
 *                                                  new DataSearchFilter(search,metaData.getParameter("filter")),
 *                                                  sorter);
 * BeanTablePanel panel = new BeanTablePanel("beanTable", provider, sorter, metaData, true, 20);
 * 
 * @author Mark Southern (mrsouthern)
 */
public class DataSearchFilter extends CriteriaBuilderDelegate implements CriteriaBuilder, Serializable
{
	private static final long serialVersionUID = 2453155853286535357L;

	private SearchPanel searchPanel;
	private String[] properties = null;
	private Set<String> aliases = new HashSet<String>();

	/**
	* Construct a new DataSearchFilter.
	*
	* @param searchPanel the Databinder SearchPanel where the user types the search criteria
	* @param properties the bean field on which to perform the search
	*/
	public DataSearchFilter(SearchPanel searchPanel, String[] properties)
	{
		this.searchPanel = searchPanel;
		setProperties(properties);
	}

	/**
	 *
	 * @param properties
	 */
	public void setProperties(String[] properties)
	{
		this.properties = properties;
		aliases = new HashSet<String>();
		if (properties != null)
		{
			for (String property : properties)
			{
				if (property.contains(".")) // i.e. property from another bean
				{
					String[] path = property.split("\\.");
					for (int ii = 0; ii < path.length - 1; ii++)
					{
						aliases.add(path[ii]);
					}
				}
			}
		}
	}

	@Override
	public void build(Criteria criteria)
	{
		super.build(criteria);
		if( searchPanel.getDefaultModelObject() != null && properties != null )
		{
			for(String alias: aliases)
			{
				criteria.createAlias(alias, alias);
			}
			Disjunction disjunction = Restrictions.disjunction();
			criteria.add(disjunction);
			for(String property: properties)
			{
				disjunction.add(Restrictions.ilike(property,
							searchPanel.getDefaultModelObject().toString(),
							MatchMode.ANYWHERE));
			}
		}
	}

}
