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
package com.googlecode.wicketwebbeans.fields;


import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;


/**
 * A Label that has a minimum size if its model is null.
 * 
 * @author Dan Syrstad
 */
public class LabelWithMinSize extends Label
{
    private static final long serialVersionUID = 1252855853289141340L;

    /**
     * Construct a new LabelWithMinSize.
     *
     * @param id
     */
    public LabelWithMinSize(String id)
    {
        super(id);
    }

    /**
     * Construct a new LabelWithMinSize.
     *
     * @param id
     * @param label
     */
    public LabelWithMinSize(String id, String label)
    {
        super(id, label);
    }

    /**
     * Construct a new LabelWithMinSize.
     *
     * @param id
     * @param model
     */
    public LabelWithMinSize(String id, IModel model)
    {
        super(id, model);
    }

    /**
     * {@inheritDoc}
     * @param markupStream
     * @param openTag 
     * @see org.apache.wicket.markup.html.basic.Label#onComponentTagBody(wicket.markup.MarkupStream, org.apache.wicket.markup.ComponentTag)
     */
    @Override
    protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag)
    {
        String value = getDefaultModelObjectAsString();
        if (Strings.isEmpty(value)) {
            value = "&nbsp;";
            setEscapeModelStrings(false);
        }

        replaceComponentTagBody(markupStream, openTag, value);
    }

}
