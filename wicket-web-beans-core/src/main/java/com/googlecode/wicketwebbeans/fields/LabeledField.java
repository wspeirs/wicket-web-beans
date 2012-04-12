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

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;


/**
 * A field that encloses a label and a field.
 * 
 * @author Dan Syrstad
 */
public class LabeledField extends Panel
{
    private static final long serialVersionUID = 2272855853285741120L;

    /**
     * Construct a LabeledField. 
     *
     * @param id the wicket id.
     * @param label the label component. Must have an id of "l".
     * @param field the field component. Must have an id of "f".
     */
    public LabeledField(String id, Component label, Component field)
    {
        super(id);

        add(label);
        add(field);
    }
}
