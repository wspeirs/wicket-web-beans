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

package com.googlecode.wicketwebbeans.model;

import java.io.Serializable;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.IWrapModel;
import org.apache.wicket.model.IComponentAssignedModel;
import org.apache.wicket.util.string.Strings;

/**
 * Behavior that modifies the CSS class attribute of the component's tag to be 
 * "beanFormError" if the component has an error.<p>
 * 
 * @author Dan Syrstad 
 */
public class ErrorHighlightingBehavior extends AttributeModifier
{
    private static final long serialVersionUID = 3152855853289137820L;
    
    public static final String BEAN_FORM_ERROR_CLASS = "beanFormError";

    /**
     * Construct a ErrorHighlightingBehavior. 
     *
     */
    public ErrorHighlightingBehavior()
    {
        super("class", true, new ErrorSwitchModel());
    }

    @Override
    protected String newValue(String currentValue, String replacementValue)
    {
        if (Strings.isEmpty(replacementValue)) {
            if (currentValue == null) {
                return "";
            }
            
            // Remove just our class, leaving other classes.
            return currentValue.replace(BEAN_FORM_ERROR_CLASS, "").trim();
        }
        
        if (currentValue == null) {
            return replacementValue;
        }
        
        if (currentValue.contains(replacementValue + ' ')) {
            return currentValue;
        }
        
        return replacementValue + ' ' + currentValue;
    }

    private static final class ErrorSwitchModel extends Model implements IComponentAssignedModel, IWrapModel
    {
        private static final long serialVersionUID = 1L;

        private Component component;
        
        public IWrapModel wrapOnAssignment(Component component)
        {
            this.component = component;
            return this;
        }
        
        public IWrapModel getWrappedModel()
        {
            return this;
        }
        
        public Serializable getObject()
        {
            if( component != null &&  component.hasErrorMessage() ) {
                return BEAN_FORM_ERROR_CLASS;
            } else {
                return "";
            }
        }

        public void setObject(Object object)
        {
            throw new UnsupportedOperationException();
        }
    }
}
