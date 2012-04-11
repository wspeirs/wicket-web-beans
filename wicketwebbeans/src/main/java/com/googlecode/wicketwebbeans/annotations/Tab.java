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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Tab annotation. Specified within a {@link Bean} annotation. 
 * <p>
 * 
 * @author Dan Syrstad
 */
@Documented
@Target({TYPE}) @Retention(RUNTIME)
public @interface Tab {
    /** The tab name. */
    String name() default "";

    /** The label to display for this element. */
    String label() default "";

    /** Defines the order of the properties in the tab. If not defined, this defaults to all of the properties not explicitly defined in
     * another tab. */
    Property[] properties() default {};
    /** A short-cut to in place of specifying individual Property annotations. Simply specify the property names. 
     * This may include "-propertyName" to remove a property. You may specify "action.actionMethodName" to 
     * include an action.  Also, Property.EMPTY can be used to specify an empty cell. */
    String[] propertyNames() default {}; 

    /** Arbitrary non-standard parameters. These are interpreted by the component. */
    Parameter[] params() default {};
    /** Short-cut to specify a single parameter. This is the parameter's name. */
    String paramName() default "";
    /** Short-cut to specify a single parameter. This is the parameter's value. */
    String paramValue() default "";
}
