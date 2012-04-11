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

import java.util.Arrays;

import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.IModel;

import com.googlecode.wicketwebbeans.model.ElementMetaData;


/**
 * A Field for a selecting zero or more elements from collection of Java Enum types. Presents the values as a list.
 * 
 * @author Dan Syrstad
 */
public class MultiSelectEnumField extends AbstractField
{
    private static final long serialVersionUID = 1492855853289132920L;

    /**
     * Construct a new MultiSelectEnumField.
     *
     * @param id the Wicket id for the editor.
     * @param model the model.
     * @param metaData the meta data for the property.
     * @param viewOnly true if the component should be view-only.
     */
    public MultiSelectEnumField(String id, IModel model, ElementMetaData metaData, boolean viewOnly)
    {
        super(id, model, metaData, viewOnly);
        
        final Class elementType = metaData.getElementType(null);
        if (elementType == null) {
            throw new RuntimeException("Cannot find elementType for property " + metaData.getPropertyName());
        }
        
        Object[] enumValues = elementType.getEnumConstants();

        ListMultipleChoice multChoice = new ListMultipleChoice("component", model, Arrays.asList(enumValues));
        multChoice.setEnabled(!viewOnly);
        
        add(multChoice);
    }
}
