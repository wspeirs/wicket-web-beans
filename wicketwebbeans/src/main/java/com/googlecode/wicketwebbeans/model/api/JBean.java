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


import org.apache.wicket.markup.html.panel.Panel;

import com.googlecode.wicketwebbeans.annotations.Action;
import com.googlecode.wicketwebbeans.annotations.Bean;
import com.googlecode.wicketwebbeans.annotations.Parameter;
import com.googlecode.wicketwebbeans.annotations.Property;
import com.googlecode.wicketwebbeans.annotations.Tab;


/**
 * Bean API implementation. Provides information from the {@link Bean} annotation in a Java API form. <p>
 * 
 * @author Dan Syrstad
 */
public class JBean implements Bean, Serializable
{
    private static final long serialVersionUID = 2092855853289294120L;

    private Class<?> type = Object.class;
    private String context =  "";
    private String extendsContext =  "";
    private String label =  "";
    private int columns =  3;
    private int rows =  10;
    private boolean displayed =  true;
    private boolean[] viewOnly =  {};
    private boolean explicitOnly = false;
    private List<Action> actions = new ArrayList<Action>();
    private String[] actionNames =  {};
    private List<Property> properties = new ArrayList<Property>();
    private String[] propertyNames =  {}; 
    private List<Tab> tabs = new ArrayList<Tab>();
    private Class<? extends Panel> container = Panel.class;
    private List<Parameter> params = new ArrayList<Parameter>();
    private String css = "";
    private String dynamicCss = "";
    private String paramName = "";
    private String paramValue = "";

    /**
     * Construct a JBean. 
     *
     */
    public JBean()
    {
    }

    /**
     * Construct a JBean. 
     *
     */
    public JBean(Class<?> type)
    {
        this.type = type;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#actionNames()
     */
    public String[] actionNames()
    {
        return actionNames;
    }

    /** @see #actionNames() */
    public JBean actionNames(String... actionNames)
    {
        this.actionNames = actionNames; 
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#actions()
     */
    public Action[] actions()
    {
        return actions.toArray(new Action[ actions.size() ]);
    }
    
    /** @see #actions() */
    public JBean actions(Action... actions)
    {
        this.actions = new ArrayList<Action>( Arrays.asList(actions) );
        return this;
    }

    /** @see #actions() */
    public JBean actions(List<Action> actions)
    {
        this.actions = actions;
        return this;
    }

    /** @see #actions() */
    public JBean add(Action action)
    {
        this.actions.add(action);
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#columns()
     */
    public int columns()
    {
        return columns;
    }

    /** @see #columns() */
    public JBean columns(int columns)
    {
        this.columns = columns;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#container()
     */
    public Class<? extends Panel> container()
    {
        return container;
    }
    
    /** @see #container() */
    public JBean container(Class<? extends Panel> container)
    {
        this.container = container;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#context()
     */
    public String context()
    {
        return context;
    }
    
    /** @see #context() */
    public JBean context(String context)
    {
        this.context = context;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#extendsContext()
     */
    public String extendsContext()
    {
        return extendsContext;
    }
    
    /** @see #extendsContext() */
    public JBean extendsContext(String extendsContext)
    {
        this.extendsContext = extendsContext;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#displayed()
     */
    public boolean displayed()
    {
        return displayed;
    }
    
    /** @see #displayed() */
    public JBean displayed(boolean displayed)
    {
        this.displayed = displayed;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#label()
     */
    public String label()
    {
        return label;
    }
    
    public JBean label(String label)
    {
        this.label = label;
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
    public JBean css(String css)
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
    public JBean dynamicCss(String dynamicCss)
    {
        this.dynamicCss = dynamicCss;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#paramName()
     */
    public String paramName()
    {
        return paramName;
    }
    
    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#paramValue()
     */
    public String paramValue()
    {
        return paramValue;
    }

    /** 
     * Shortcut for {@link #paramName()} and {@link #paramValue()}.
     */
    public JBean param(String name, String value)
    {
        paramName = name;
        paramValue = value;
        return this;
    }
    
    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#params()
     */
    public Parameter[] params()
    {
        return params.toArray(new Parameter[ params.size() ]);
    }
    
    /** @see #params() */
    public JBean params(Parameter... params)
    {
        this.params = new ArrayList<Parameter>( Arrays.asList(params) ); // Must be mutable
        return this;
    }
    
    /** @see #params() */
    public JBean params(List<Parameter> params)
    {
        this.params = params;
        return this;
    }
    
    /** @see #params() */
    public JBean add(Parameter param)
    {
        this.params.add(param);
        return this;
    }

    /** 
     * Shortcut for {@link #paramName()} and {@link #paramValue()}. 
     */
    public JBean add(String name, String... values)
    {
        params.add( new JParameter(name, values) );
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#properties()
     */
    public Property[] properties()
    {
        return properties.toArray(new Property[ properties.size() ]);
    }
    
    /** @see #properties() */
    public JBean properties(Property... properties)
    {
        this.properties = new ArrayList<Property>( Arrays.asList(properties) ); // Must be mutable
        return this;
    }
    
    /** @see #properties() */
    public JBean properties(List<Property> properties)
    {
        this.properties = properties;
        return this;
    }
    
    /** @see #properties() */
    public JBean add(Property property)
    {
        this.properties.add(property);
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#propertyNames()
     */
    public String[] propertyNames()
    {
        return propertyNames;
    }

    /** @see #propertyNames() */
    public JBean propertyNames(String... propertyNames)
    {
        this.propertyNames = propertyNames;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#rows()
     */
    public int rows()
    {
        return rows;
    }
    
    /** @see #rows() */
    public JBean rows(int rows)
    {
        this.rows = rows;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#tabs()
     */
    public Tab[] tabs()
    {
        return tabs.toArray(new Tab[tabs.size()]);
    }

    /** @see #tabs() */
    public JBean tabs(Tab... tabs)
    {
        this.tabs = new ArrayList<Tab>( Arrays.asList(tabs) ); // Must be mutable
        return this;
    }
    
    /** @see #tabs() */
    public JBean tabs(List<Tab> tabs)
    {
        this.tabs = tabs;
        return this;
    }
    
    /** @see #tabs() */
    public JBean add(Tab tab)
    {
        this.tabs.add(tab);
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#type()
     */
    public Class<?> type()
    {
        return type;
    }
    
    /** @see #type() */
    public JBean type(Class<?> type)
    {
        this.type = type;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#viewOnly()
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
    public JBean viewOnly(boolean flag)
    {
        this.viewOnly = new boolean[] { flag };
        return this;
    }

    /**
     * {@inheritDoc}
     * @see com.googlecode.wicketwebbeans.annotations.Bean#explicitOnly()
     */
    public boolean explicitOnly()
    {
        return explicitOnly;
    }

    /** @see #displayed() */
    public JBean explicitOnly(boolean explicitOnly)
    {
        this.explicitOnly = explicitOnly;
        return this;
    }

    /** 
     * {@inheritDoc}
     * @see java.lang.annotation.Annotation#annotationType()
     */
    public Class<? extends Annotation> annotationType()
    {
        return Bean.class;
    }
}
