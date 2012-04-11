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
import com.googlecode.wicketwebbeans.fields.Field;



/**
 * Bean Property API implementation. Provides information from the {@link Property} annotation in a Java API form. <p>
 * 
 * @author Dan Syrstad
 */
public class JProperty implements Property, Serializable
{
    private static final long serialVersionUID = 2222855853289147190L;

    private String name = "";
    private boolean required = false;
    private int maxLength = 0;
    private String defaultValue = "";
    private Class<? extends Field> fieldType = Field.class;
    private Class<?> elementType = Object.class;
    private int rows = 0;
    private int columns = 0;
    private boolean[] viewOnly = {};

    // --- Common to Action and Property
    private String label =  "";
    private String labelImage = "";
    private int colspan = 1;
    private String css = "";
    private String dynamicCss = "";
    private List<Parameter> params = new ArrayList<Parameter>();
    private String paramName = "";
    private String paramValue = "";

    /**
     * Construct a JProperty. 
     *
     */
    public JProperty()
    {
    }

    /**
     * Construct a JProperty. 
     *
     */
    public JProperty(String name)
    {
        this.name = name;  
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Property#colspan()
     */
    public int colspan()
    {
        return this.colspan;
    }
    
    public JProperty colspan(int colspan)
    {
        this.colspan = colspan;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Property#columns()
     */
    public int columns()
    {
        return columns;
    }

    /** @see #columns() */
    public JProperty columns(int columns)
    {
        this.columns = columns;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Property#defaultValue()
     */
    public String defaultValue()
    {
        return defaultValue;
    }
    
    /** @see #defaultValue() */
    public JProperty defaultValue(String defaultValue)
    {
        this.defaultValue = defaultValue;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Property#elementType()
     */
    public Class<?> elementType()
    {
        return this.elementType;
    }
    
    /** @see #elementType() */
    public JProperty elementType(Class<?> elementType)
    {
        this.elementType = elementType;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Property#fieldType()
     */
    public Class<? extends Field> fieldType()
    {
        return fieldType;
    }
    
    /** @see #fieldType() */
    public JProperty fieldType(Class<? extends Field> fieldType)
    {
        this.fieldType = fieldType;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Property#label()
     */
    public String label()
    {
        return label;
    }
    
    public JProperty label(String label)
    {
        this.label = label;
        return this;
    }
    
    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Property#labelImage()
     */
    public String labelImage()
    {
        return labelImage;
    }
    
    /** @see #labelImage() */
    public JProperty labelImage(String labelImage)
    {
        this.labelImage = labelImage;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Property#maxLength()
     */
    public int maxLength()
    {
        return maxLength;
    }
    
    /** @see #maxLength() */
    public JProperty maxLength(int maxLength)
    {
        this.maxLength = maxLength;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Property#name()
     */
    public String name()
    {
        return name;
    }

    /** @see #name() */
    public JProperty name(String name)
    {
        this.name = name;
        return this;
    }
    
    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#css()
     */
    public String css()
    {
        return css;
    }
    
    /** @see #css(). */
    public JProperty css(String css)
    {
        this.css = css;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#dynamicCss()
     */
    public String dynamicCss()
    {
        return dynamicCss;
    }

    /** @see #dynamicCss(). */
    public JProperty dynamicCss(String dynamicCss)
    {
        this.dynamicCss = dynamicCss;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Property#paramName()
     */
    public String paramName()
    {
        return paramName;
    }
    
    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Property#paramValue()
     */
    public String paramValue()
    {
        return paramValue;
    }

    /** 
     * Shortcut for {@link #paramName()} and {@link #paramValue()}.
     */
    public JProperty param(String name, String value)
    {
        paramName = name;
        paramValue = value;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Property#params()
     */
    public Parameter[] params()
    {
        return params.toArray(new Parameter[ params.size() ]);
    }
    
    /** @see #params() */
    public JProperty params(Parameter... params)
    {
        this.params = new ArrayList<Parameter>( Arrays.asList(params) ); // Must be mutable
        return this;
    }
    
    /** @see #params() */
    public JProperty params(List<Parameter> params)
    {
        this.params = params;
        return this;
    }
    
    /** @see #params() */
    public JProperty add(Parameter param)
    {
        this.params.add(param);
        return this;
    }

    /** 
     * Shortcut for {@link #paramName()} and {@link #paramValue()}. 
     */
    public JProperty add(String name, String... values)
    {
        params.add( new JParameter(name, values) );
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Property#required()
     */
    public boolean required()
    {
        return required;
    }
    
    /** @see #required() */
    public JProperty required(boolean required)
    {
        this.required = required;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Property#rows()
     */
    public int rows()
    {
        return rows;
    }
    
    /** @see #rows() */
    public JProperty rows(int rows)
    {
        this.rows = rows;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Property#viewOnly()
     */
    public boolean[] viewOnly()
    {
        return viewOnly;
    }
    
    /**
     * @return true if viewOnly is set, else false. Simplified version of {@link #viewOnly()}. Returns false if viewOnly has not been set on this bean. 
     */
    public boolean viewOnlyFlag()
    {
        return viewOnly.length > 0 ? viewOnly[0] : false;
    }
    
    /** @see #viewOnly() */
    public JProperty viewOnly(boolean flag)
    {
        this.viewOnly = new boolean[] { flag };
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see java.lang.annotation.Annotation#annotationType()
     */
    public Class<? extends Annotation> annotationType()
    {
        return Property.class;
    }

}
