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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.wicketwebbeans.annotations.Bean;
import com.googlecode.wicketwebbeans.annotations.Beans;



/**
 * Beans API implementation. Provides information from the {@link Beans} annotation in a Java API form. <p>
 * 
 * @author Dan Syrstad
 */
public class JBeans implements Beans, Serializable
{
    private static final long serialVersionUID = 1092855853289149820L;

    private List<Bean> value = new ArrayList<Bean>();

    /**
     * Construct a JBeans. 
     */
    public JBeans()
    {
    }

    /**
     * Construct a JBeans. 
     */
    public JBeans(Bean... beans)
    {
        value(beans);
    }

    /**
     * Construct a JBeans. 
     */
    public JBeans(List<Bean> beans)
    {
        value(beans);
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Beans#value()
     */
    public Bean[] value()
    {
        return value.toArray(new Bean[ value.size() ]);
    }
    
    /** @see #value() */
    public JBeans value(Bean... beans)
    {
        value = new ArrayList<Bean>( Arrays.asList(beans) ); // Need a mutable form.
        return this;
    }

    /** @see #value() */
    public JBeans value(List<Bean> beans)
    {
        value = beans;
        return this;
    }
    
    /** @see #value() */
    public JBeans add(Bean bean)
    {
        value.add(bean);
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see java.lang.annotation.Annotation#annotationType()
     */
    public Class<? extends Annotation> annotationType()
    {
        return Beans.class;
    }

}
