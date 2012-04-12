/*---
   Copyright 2007 Visual Systems Corporation.
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

package com.googlecode.wicketwebbeans.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.containers.BeanGridPanel;



/**
 * Action annotation. May be specified within a {@link Bean} annotation or on the action method itself. 
 * <p>
 * 
 * @author Dan Syrstad
 */
@Documented
@Target({TYPE, METHOD}) @Retention(RUNTIME)
public @interface Action  {
    /** The action name. You may also specify "-actionName" to remove an action. */
    String name() default "";

    /** Defines a Javascript confirmation message to display when the button is clicked. If answer is "Yes", the action proceeds, otherwise it is canceled. */
    String confirm() default "";
    
    /** If true, the action should be invoked with Ajax. Otherwise, it the action performs a regular form submit. */
    boolean ajax() default false; 
  
    /** If true, the action will be fired when the Enter key is pressed anywhere on the form. */
    boolean isDefault() default false;

    // --- Common to Action and Property
    /** The label to display for this element. */
    String label() default "";

    /** An image file name relative to the component. Specifies that the property's field should have the specified image rather than a text label. */
    String labelImage() default "";

    /** Number of columns to span in the grid. Though not strictly a "standard" parameter, this is a parameter supported by {@link BeanGridPanel}, 
     * which is the default layout used by {@link BeanForm}. See {@link BeanGridPanel} parameters for more details. */
    int colspan() default 1;

    /** Arbitrary non-standard parameters. These are interpreted by the component. */
    Parameter[] params() default {};
    /** Short-cut to specify a single parameter. This is the parameter's name. */
    String paramName() default "";
    /** Short-cut to specify a single parameter. This is the parameter's value. */
    String paramValue() default "";

    // End Common to Action and Property ---
}
