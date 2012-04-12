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


import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.googlecode.wicketwebbeans.model.ElementMetaData;


/**
 * Stub Field that displays nothing. Can be used to keep a layout cell blank.
 *
 * @author Dan Syrstad
 */
public class EmptyField extends Panel implements Field
{
    private static final long serialVersionUID = 3418855853285941120L;

    /**
     * Construct a new EmptyField.
     *
     * @param id the Wicket id for the editor.
     * @param model the model.
     * @param metaData the meta data for the property.
     * @param viewOnly true if the component should be view-only.
     */
    public EmptyField(String id, IModel model, ElementMetaData metaData, boolean viewOnly)
    {
        super(id);
        setRenderBodyOnly(true);
    }
}
