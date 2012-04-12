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


import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;

import com.googlecode.wicketwebbeans.model.ElementMetaData;


/**
 * A text area Field. Accepts parameters for "rows" and/or "cols".
 * 
 * @author Dan Syrstad
 */
public class TextAreaField extends AbstractField
{
    private static final long serialVersionUID = 1862855853289214820L;

    /**
     * Construct a new TextAreaField.
     *
     * @param id the Wicket id for the editor.
     * @param model the model.
     * @param metaData the meta data for the property.
     * @param viewOnly true if the component should be view-only.
     */
    public TextAreaField(String id, IModel model, final ElementMetaData metaData, boolean viewOnly)
    {
        super(id, model, metaData, viewOnly);

        metaData.consumeParameter(ElementMetaData.PARAM_ROWS);
        metaData.consumeParameter(ElementMetaData.PARAM_COLUMNS);
        TextArea field = new TextArea("component", model) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void onComponentTag(ComponentTag tag)
            {
                super.onComponentTag(tag);
                String rows = metaData.getParameter("rows");
                if (rows != null) {
                    tag.put("rows", rows);
                }

                String cols = metaData.getParameter("cols");
                if (cols != null) {
                    tag.put("cols", cols);
                }
            }
        };
        
        setFieldParameters(field);
        field.setEnabled(!viewOnly);

        add(field);
    }
}
