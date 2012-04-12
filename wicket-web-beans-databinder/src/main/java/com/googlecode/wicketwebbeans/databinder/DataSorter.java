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

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;

import net.databinder.models.hib.OrderingCriteriaBuilder;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

/**
 * A Databinder/Hibernate sorting component suitable for adding to a BeanTablePanel in conjunction
 * with a DatabinderProvider. A typical use case might be:
 * 
 *  DataSorter sorter = new DataSorter(metaData.getParameter("orderBy"));
 *  IDataProvider provider = new DatabinderProvider(beanClass,
 *                                                  new DataFilter(metaData.getParameter("filter")),
 *                                                  sorter);
 *  BeanTablePanel panel = new BeanTablePanel("beanTable", provider, sorter, metaData, true, 20);
 * 
 * @author Mark Southern (mrsouthern)
 * @deprecated use net.databinder.models.hib.CriteriaSorter instead. Class to be removed at a later date
 */
@Deprecated
public class DataSorter implements ISortStateLocator, OrderingCriteriaBuilder, Serializable
{
    private static final long serialVersionUID = 2222855853286535147L;

    private SingleSortState sortState = new SingleSortState();
    private String defaultProperty = null;
    boolean asc = true;
    
    /**
     * Construct a new DataSorter with no default sort
     */
    public DataSorter()
    {
        
    }

    /**
     * Construct a new DataSorter with a default sort.
     *
     * @param defaultProperty the default sort.
     */
    public DataSorter(String defaultProperty)
    {
        this(defaultProperty,true);
    }
    
    /**
     * Construct a new DataSorter.
     *
     * @param defaultProperty the default sort.
     * @param asc sort ascending/descending
     */
    public DataSorter(String defaultProperty, boolean asc)
    {
        this.defaultProperty = defaultProperty;
        this.asc = asc;
    }
    
    public void buildUnordered(Criteria criteria) {
    	
    }

    public void buildOrdered(Criteria criteria)
    {
        String property;
        SortParam sort = sortState.getSort();
        if( sort != null && sort.getProperty() != null )
        {
            property  = sort.getProperty();
            asc = sort.isAscending();
        }
        else {
            property = defaultProperty;
        }
        if( property != null )
        {
            if( property.contains(".") )
            {
                String[] path = property.split("\\.");
                for( int ii = 0; ii < path.length - 1; ii++ ) {
                    criteria.createAlias(path[ii], path[ii]);
                }
            }
            
            criteria.addOrder(asc ? Order.asc(property) : Order.desc(property));
        }
    }
    
    public ISortState getSortState()
    {
        return sortState;
    }
    
    public void setSortState(ISortState state)
    {
        sortState = (SingleSortState) state;
    }
}