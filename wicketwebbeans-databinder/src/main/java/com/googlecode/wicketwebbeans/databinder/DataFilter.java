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
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.util.lang.PropertyResolver;
import net.databinder.models.hib.CriteriaBuilder;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * A Databinder/Hibernate filtering component suitable for adding to a BeanTablePanel in conjunction
 * with a DatabinderProvider. The bean field that can be searched is specified in the constructor
 * A typical use case might be:
 * 
 * DataSorter sorter = new DataSorter(metaData.getParameter("orderBy"));
 * IDataProvider provider = new DatabinderProvider(beanClass,
 *                                                  new DataFilter(metaData.getParameter("filter")),
 *                                                  sorter);
 * BeanTablePanel panel = new BeanTablePanel("beanTable", provider, sorter, metaData, true, 20);
 * 
 * @author Mark Southern (mrsouthern)
 */
public class DataFilter implements IFilterStateLocator, CriteriaBuilder, Serializable
{
	private static final long serialVersionUID = 1412855853286535315L;

	private Object beanState;
	private String defaultProperty = null;

	/**
	* Construct a new DataFilter.
	*
	* @param defaultProperty the bean field on which to perform the search
	*/
	public DataFilter(String defaultProperty)
	{
		this.defaultProperty = defaultProperty;
	}

	public void build(Criteria criteria)
	{
		Object name = PropertyResolver.getValue(defaultProperty, beanState);
		if (name != null && name instanceof String )
		{
			criteria.add(Restrictions.like(defaultProperty, name.toString(), MatchMode.ANYWHERE));
		}
	}

	public Object getFilterState()
	{
		return beanState;
	}

	public void setFilterState(Object beanState)
	{
		this.beanState = beanState;
	}

}
