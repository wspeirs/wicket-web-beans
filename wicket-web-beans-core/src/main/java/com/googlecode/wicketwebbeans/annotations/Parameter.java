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
 * Parameter annotation. Specified within a {@link Bean}, {@link Tab}, {@link Property}, or {@link Action} annotation
 * to supply non-standard parameters. 
 * <p>
 * 
 * @author Dan Syrstad
 */
@Documented
@Target({TYPE}) @Retention(RUNTIME)
public @interface Parameter {
    /** The parameter name. */
    String name();
    
    /** The parameter's string value. Also supports multi-valued parameters. */
    String[] value() default "";
}
