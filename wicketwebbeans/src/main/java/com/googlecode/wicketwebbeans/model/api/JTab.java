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

import com.googlecode.wicketwebbeans.annotations.Parameter;
import com.googlecode.wicketwebbeans.annotations.Property;
import com.googlecode.wicketwebbeans.annotations.Tab;



/**
 * Bean Tab API implementation. Provides information from the {@link Tab} annotation in a Java API form. <p>
 * 
 * @author Dan Syrstad
 */
public class JTab implements Tab, Serializable
{
    private static final long serialVersionUID = 2102855853289191520L;

    private String name = "";
    private String label = "";
    private List<Property> properties = new ArrayList<Property>();
    private String[] propertyNames =  {}; 
    private List<Parameter> params = new ArrayList<Parameter>();
    private String paramName = "";
    private String paramValue = "";

    /**
     * Construct a JTab. 
     */
    public JTab()
    {
    }

    /**
     * Construct a JTab. 
     */
    public JTab(String name)
    {
        this.name = name;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Tab#label()
     */
    public String label()
    {
        return label;
    }
    
    public JTab label(String label)
    {
        this.label = label;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Tab#properties()
     */
    public Property[] properties()
    {
        return properties.toArray(new Property[ properties.size() ]);
    }
    
    /** @see #properties() */
    public JTab properties(Property... properties)
    {
        this.properties = new ArrayList<Property>( Arrays.asList(properties) ); // Must be mutable
        return this;
    }
    
    /** @see #properties() */
    public JTab properties(List<Property> properties)
    {
        this.properties = properties;
        return this;
    }
    
    /** @see #properties() */
    public JTab add(Property property)
    {
        this.properties.add(property);
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Tab#propertyNames()
     */
    public String[] propertyNames()
    {
        return propertyNames;
    }

    /** @see #propertyNames() */
    public JTab propertyNames(String... propertyNames)
    {
        this.propertyNames = propertyNames;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Tab#paramName()
     */
    public String paramName()
    {
        return paramName;
    }
    
    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Tab#paramValue()
     */
    public String paramValue()
    {
        return paramValue;
    }

    /** 
     * Shortcut for {@link #paramName()} and {@link #paramValue()}.
     */
    public JTab param(String name, String value)
    {
        paramName = name;
        paramValue = value;
        return this;
    }
    
    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Tab#params()
     */
    public Parameter[] params()
    {
        return params.toArray(new Parameter[ params.size() ]);
    }
    
    /** @see #params() */
    public JTab params(Parameter... params)
    {
        this.params = new ArrayList<Parameter>( Arrays.asList(params) ); // Must be mutable
        return this;
    }
    
    /** @see #params() */
    public JTab params(List<Parameter> params)
    {
        this.params = params;
        return this;
    }
    
    /** @see #params() */
    public JTab add(Parameter param)
    {
        this.params.add(param);
        return this;
    }

    /** 
     * Shortcut for {@link #paramName()} and {@link #paramValue()}. 
     */
    public JTab add(String name, String... values)
    {
        params.add( new JParameter(name, values) );
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Tab#name()
     */
    public String name()
    {
        return name;
    }
    
    /** @see #name() */
    public JTab name(String name)
    {
        this.name = name;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see java.lang.annotation.Annotation#annotationType()
     */
    public Class<? extends Annotation> annotationType()
    {
        return Tab.class;
    }
}
