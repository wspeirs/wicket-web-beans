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


import java.io.Serializable;
import java.util.List;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;

import com.googlecode.wicketwebbeans.model.ElementMetaData;

/**
 * A Field that displays a DropDownChoice with a bound list of beans. A ChoiceRenderer is used
 * to display the beans value.
 * 
 * To use this Field, subclass it with your own data and the default 4 argument constructor.
 * (String,IModel,ElementMetaData,boolean)
 * 
 * @author Mark Southern (mrsouthern)
 */
public abstract class DropDownChoiceField extends AbstractField
{
    private DropDownChoice choice;

    /**
     * Construct a new DropDownChoiceField.
     *
     * @param id the Wicket id for the editor.
     * @param model the model.
     * @param metaData the meta data for the property.
     * @param viewOnly true if the component should be view-only.
     * @param valueModel the Bean List of drop down choices
     * @param choiceRenderer displays the bean value
     *  produce the value displayed to the user.
     */
    public DropDownChoiceField(String id, IModel<Serializable> model, ElementMetaData metaData, boolean viewOnly, IModel<? extends List<? extends Serializable>> valueModel, final IChoiceRenderer choiceRenderer)
    {
        super(id, model, metaData, viewOnly);

        Fragment fragment;
        if (viewOnly) {
            fragment = new Fragment("frag", "viewer", this);
            fragment.add(new LabelWithMinSize("component", model)
            {
                protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag)
                {
                    String value = choiceRenderer.getDisplayValue(getDefaultModelObject()).toString();
                    if (Strings.isEmpty(value))
                    {
                        value = "&nbsp;";
                        setEscapeModelStrings(false);
                    }
                    replaceComponentTagBody(markupStream, openTag, value);
                }
            });
        }
        else {
            fragment = new Fragment("frag", "editor", this);
            choice = new DropDownChoice("component", model, valueModel, choiceRenderer);
            // Always allow the null choice.
            choice.setNullValid(true);
            fragment.add(choice);
        }
        add(fragment);
    }
}