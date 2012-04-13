/*---
   Copyright 2006-2007 Visual Systems Corporation.
   http://www.vscorp.com

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
/*
 * $Id: DefaultDataTable.java 4013 2006-01-30 21:56:23 +0000 (Mon, 30 Jan 2006)
 * ivaynberg $ $Revision: 1.1 $ $Date: 2006-01-30 21:56:23 +0000 (Mon, 30 Jan
 * 2006) $
 * 
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.googlecode.wicketwebbeans.containers;

import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackHeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

import com.googlecode.wicketwebbeans.model.BeanMetaData;


/**
 * An implementation of the DataTable that aims to solve the 90% usecase by
 * adding headers, an no-records-found toolbars to a standard
 * {@link DataTable}. Unlike {@link AjaxFallbackDefaultDataTable}, this class
 * does not add the {@link NavigationToolbar} as part of the table. Hence the table can scroll
 * horizontally independent of the navigation.
 * <p>
 * The {@link HeadersToolbar} is added as a top
 * toolbar, while the {@link NoRecordsToolbar} toolbar is added as a bottom
 * toolbar.
 * 
 * @param <T>
 * @see DataTable
 * @see HeadersToolbar
 * @see NavigationToolbar
 * @see NoRecordsToolbar
 * 
 * @author Igor Vaynberg ( ivaynberg )
 * @author Dan Syrstad (From AjaxFallbackDefaultDataTable).
 * @author Mark Southern (mrsouthern)
 */
public class BeanDataTable<T> extends DataTable<T>
{
    private static final long serialVersionUID = 1L;

    private BeanMetaData beanMetaData;

    /**
     * Constructor
     * 
     * @param id component id
     * @param columns list of columns
     * @param dataProvider data provider
     * @param rowsPerPage number of rows per page
     * @param metaData
     */
    public BeanDataTable(String id,
            final List<IColumn<T>>columns,
            ISortableDataProvider<T> dataProvider,
            int rowsPerPage,
            BeanMetaData metaData)
    {
        this(id, columns, dataProvider, dataProvider, rowsPerPage, metaData);
    }

    /**
     * Constructor
     * 
     * @param id component id
     * @param columns List of columns
     * @param dataProvider data provider
     * @param sortStateLocator sorter
     * @param rowsPerPage number of rows per page
     * @param metaData
     */
    public BeanDataTable(String id,
            final List<IColumn<T>> columns,
            IDataProvider<T> dataProvider,
            ISortStateLocator sortStateLocator,
            int rowsPerPage,
            BeanMetaData metaData)
    {
        super(id, columns, dataProvider, rowsPerPage);
        this.beanMetaData = metaData;
        setOutputMarkupId(true);
        setVersioned(false);
        addTopToolbar(new AjaxFallbackHeadersToolbar(this, sortStateLocator));
        addBottomToolbar(new NoRecordsToolbar(this));
    }

    /**
     *
     * @param id
     * @param index
     * @param model
     * @return
     */
    protected Item<T> newRowItem(String id, int index, IModel<T> model)
    {
        Item<T> item = new OddEvenItem<T>(id, index, model);
        beanMetaData.applyCss(model.getObject(), beanMetaData, item);
        return item;
    }

}
