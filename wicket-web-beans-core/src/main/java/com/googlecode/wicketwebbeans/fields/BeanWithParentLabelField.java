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
 * 
 */
package com.googlecode.wicketwebbeans.fields;


import org.apache.wicket.model.IModel;

import com.googlecode.wicketwebbeans.containers.BeanGridPanel;
import com.googlecode.wicketwebbeans.model.BeanMetaData;
import com.googlecode.wicketwebbeans.model.ElementMetaData;


/**
 * A Field that presents a property's bean in a panel, but 
 * the bean's properties do not have labels. For errors, the bean's properties use the 
 * parent property's label. This is useful for creating simple composite fields directly from
 * small beans. 
 *
 * @author Dan Syrstad
 */
public class BeanWithParentLabelField extends AbstractField
{
    private static final long serialVersionUID = 4412855853289276110L;

    /**
     * Construct a new BeanWithParentLabelField.
     *
     * @param id the Wicket id for the editor.
     * @param model the model.
     * @param metaData the meta data for the property.
     * @param viewOnly true if the component should be view-only.
     */
    public BeanWithParentLabelField(String id, IModel model, ElementMetaData metaData, boolean viewOnly)
    {
        super(id, model, metaData, viewOnly);
        
        BeanMetaData beanMetaData = metaData.createBeanMetaData(viewOnly);
        // Rename the bean's property labels to this property's label.
        for (ElementMetaData prop : beanMetaData.getDisplayedElements()) {
            if (!prop.isAction()) {
                prop.setLabel(metaData.getLabel());
            }
        }
        
        if (!viewOnly && model.getObject() == null) {
            // Create a blank instance for editing.
            model.setObject( metaData.createInstance() );
        }

        add( new BeanGridPanel("p", model, beanMetaData, null, false) );
    }
}
