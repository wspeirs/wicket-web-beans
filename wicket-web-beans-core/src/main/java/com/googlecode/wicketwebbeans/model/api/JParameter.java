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

package com.googlecode.wicketwebbeans.model.api;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import com.googlecode.wicketwebbeans.annotations.Parameter;



/**
 * Bean Parameter API implementation. Provides information from the {@link Parameter} annotation in a Java API form. <p>
 * 
 * @author Dan Syrstad
 */
public class JParameter implements Parameter, Serializable
{
    private static final long serialVersionUID = 2212855853289415120L;

    private String name = "";
    private String[] value = {};
    
    /**
     * Construct a JParameter. 
     */
    public JParameter(String name, String[] values)
    {
        this.name = name;
        this.value = values;
    }

    /**
     * Construct a JParameter. 
     */
    public JParameter(String name, String value)
    {
        this(name, new String[] { value });
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Parameter#name()
     */
    public String name()
    {
        return name;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Parameter#value()
     */
    public String[] value()
    {
        return value;
    }

    /** 
     * {@inheritDoc}
     * @see java.lang.annotation.Annotation#annotationType()
     */
    public Class<? extends Annotation> annotationType()
    {
        return Parameter.class;
    }

}
