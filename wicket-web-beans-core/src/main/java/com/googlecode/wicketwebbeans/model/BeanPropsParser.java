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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.wicketwebbeans.annotations.Action;
import com.googlecode.wicketwebbeans.annotations.Property;
import com.googlecode.wicketwebbeans.annotations.Tab;
import com.googlecode.wicketwebbeans.model.api.JAction;
import com.googlecode.wicketwebbeans.model.api.JBean;
import com.googlecode.wicketwebbeans.model.api.JBeans;
import com.googlecode.wicketwebbeans.model.api.JProperty;
import com.googlecode.wicketwebbeans.model.api.JTab;



/**
 * A recursive descent parser to parse a ".beanprops" stream. <p>
 * 
 * @author Dan Syrstad 
 */
public class BeanPropsParser
{
    private String streamName;
    private InputStream stream;
    private StreamTokenizer tokenizer;
    private BeanMetaData beanMetaData;

    /**
     * Construct a BeanPropsParser. 
     *
     * @param streamName
     * @param stream
     */
    public BeanPropsParser(String streamName, InputStream stream)
    {
        this.streamName = streamName;
        this.stream = stream;
    }
    
    /**
     * Generate a error message via an exception.
     */
    private RuntimeException generateError(String msg) 
    {
        return new RuntimeException("Error: " + streamName + " at line " + tokenizer.lineno() + ": " + msg);
    }
    
    /**
     * @return the current token from the tokenizer. Note that this returns an empty string rather than null.
     */
    private String getToken()
    {
        String value = tokenizer.sval;
        if (value == null && tokenizer.ttype >= 0) {
            value = String.valueOf((char) tokenizer.ttype);
        }
        
        return value == null ? "" : value;
    }
    
    /**
     * Gets the next token from the tokenizer. Note that this returns an empty string rather than null.
     * This is a combination of tokenizer.nextToken() and getToken(). 
     *
     * @return the next token.
     */
    private String getNextToken()
    {
        try {
            if (tokenizer.nextToken() == StreamTokenizer.TT_EOF) {
                throw new RuntimeException("Unexpected EOF reading from " + streamName);
            }
            
            return getToken();
        }
        catch (IOException e) {
            throw new RuntimeException("Error reading from " + streamName, e);
        }
        
    }
    
    /**
     * Generate an "expected" error message via an exception.
     */
    private RuntimeException generateExpectedError(String expectedThing) 
    {
        return generateError("Expected '" + expectedThing + "', but got '" + getToken() + "'");
    }
    
    /**
     * Reads and expects a token.
     *
     * @param token
     */
    private void expect(String token)
    {
        if (!getNextToken().equals(token)) {
            throw generateExpectedError(token);
        }
    }
    
    /**
     * Parses the stream given on construction and returns the BeanASTs.
     *
     * @return a list of BeanASTs.
     * 
     * @throws RuntimeException if a parsing error occurs.
     */
    public List<BeanAST> parse()
    {
        tokenizer = new StreamTokenizer( new BufferedReader( new InputStreamReader(stream) ) );
        tokenizer.resetSyntax();
        tokenizer.wordChars('a', 'z');
        tokenizer.wordChars('A', 'Z');
        tokenizer.wordChars('0', '9');
        tokenizer.wordChars('_', '_');
        tokenizer.wordChars('-', '-');
        tokenizer.wordChars('.', '.');
        tokenizer.wordChars('*', '*');
        tokenizer.wordChars('$', '$'); // For inner classes/macros
        tokenizer.wordChars(128 + 32, 255);
        tokenizer.whitespaceChars(0, ' ');
        tokenizer.commentChar('#');
        tokenizer.quoteChar('"');
        
        List<BeanAST> beans = new ArrayList<BeanAST>();
        try {
            while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
                if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
                    beans.add( parseBean() );
                }
                else {
                    throw generateExpectedError("bean name");
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Error reading " + streamName, e);
        }
        
        return beans;
    }
    
    /**
     * Parses the stream given on construction.
     *
     * @param beanMetaData 
     * @return a JBeans object representing the parsed beans.
     * 
     * @throws RuntimeException if a parsing error occurs.
     */
    public JBeans parseToJBeans(BeanMetaData beanMetaData)
    {
        this.beanMetaData = beanMetaData;
        return processBeans( parse() );
    }

    /**
     * Process bean ASTs that apply to this bean. This does not update the meta data for this object,
     * it simply creates a JBeans object containing the metadata.
     *
     * @param beans the BeanASTs.
     * 
     * @return a JBeans object containing the meta data. 
     */
    private JBeans processBeans(List<BeanAST> beans)
    {
        JBeans jbeans = new JBeans();
        
        Class<?> beanClass = beanMetaData.getBeanClass();
        String fullName = beanClass.getName();
        String baseName = BeanMetaData.getBaseClassName(beanClass); // Name without pkg but with parent of inner class
        String shortName = beanClass.getSimpleName(); // Short name without parent of inner class

        for (BeanAST bean : beans) {
            String beanName = bean.getName();
            if (shortName.equals(beanName) || baseName.equals(beanName) || fullName.equals(beanName)) {
                jbeans.add( processBean(bean) );
            }
        }
        
        return jbeans;
    }

    /**
     * Turns a BeanAST into a JBean.
     *
     * @param bean
     */
    private JBean processBean(BeanAST bean)
    {
        JBean jbean = new JBean( beanMetaData.getBeanClass() );
        jbean.context( bean.getContext() );
        jbean.extendsContext( bean.getExtendsContext() );
        
        for (ParameterAST param : bean.getParameters()) {
            jbean.add(param.getName(), param.getValuesAsStrings());
        }
        
        // Process actions first.
        for (ParameterAST param : bean.getParameters()) {
            String name = param.getName();
            if (name.equals(BeanMetaData.PARAM_ACTIONS)) {
                jbean.actions( processActions(param.getValues()) );
            }
            else if (name.equals(BeanMetaData.PARAM_PROPS)) {
                jbean.properties( processProps(param.getValues()) );
            }
            else if (param.getName().equals(BeanMetaData.PARAM_TABS)) {
                jbean.tabs( processTabs(param.getValues()) );
            }
            else {
                jbean.add(name, param.getValuesAsStrings());
            }
        }
        
        return jbean;
    }

    /**
     * Turns a BeanAST's "props" into a List of Property.
     *
     * @param values
     * 
     * @return a List of Property.
     */
    List<Property> processProps(List<ParameterValueAST> values)
    {
        List<Property> jproperties = new ArrayList<Property>();
        for (ParameterValueAST value : values) {
            String elementName = value.getValue();
            JProperty jproperty = new JProperty(elementName);
            jproperties.add(jproperty);
            for (ParameterAST param : value.getParameters()) {
                jproperty.add(param.getName(), param.getValuesAsStrings());
            }
        }
        
        return jproperties;
    }

    /**
     * Turns a BeanAST's "actions" into a List of Action.
     *
     * @param values
     * 
     * @return a List of Action.
     */
    private List<Action> processActions(List<ParameterValueAST> values)
    {
        List<Action> jactions = new ArrayList<Action>();
        for (ParameterValueAST value : values) {
            String elementName = value.getValue();
            JAction jaction = new JAction(elementName);
            jactions.add(jaction);
            for (ParameterAST param : value.getParameters()) {
                jaction.add(param.getName(), param.getValuesAsStrings());
            }
        }
        
        return jactions;
    }

    /**
     * Turns a BeanAST's "tabs" into a List of Tab.
     *
     * @param values
     * 
     * @return a List of Tab.
     */
    private List<Tab> processTabs(List<ParameterValueAST> values)
    {
        List<Tab> jtabs = new ArrayList<Tab>();
        for (ParameterValueAST value : values) {
            String elementName = value.getValue();
            JTab jtab = new JTab(elementName);
            jtabs.add(jtab);
            for (ParameterAST param : value.getParameters()) {
                if (param.getName().equals(BeanMetaData.PARAM_PROPS)) {
                    jtab.properties( processProps(param.getValues()) );
                }
                else {
                    jtab.add(param.getName(), param.getValuesAsStrings());
                }
            }
        }
        
        return jtabs;
    }

    private BeanAST parseBean()
    {
        String beanName = getToken();

        // Context "[context]" or "[context extends context]" specified?
        String context = null;
        String extendsContext = null;
        String nextToken = getNextToken();
        if (nextToken.equals("[")) {
            context = getNextToken();
            nextToken = getNextToken();
            if (nextToken.equals("extends")) {
                extendsContext = getNextToken();
                nextToken = getNextToken();
            }
            
            if (!nextToken.equals("]")) {
                throw generateExpectedError("extends' or ']");
            }
        }
        else {
            tokenizer.pushBack();
        }
        
        expect("{");
        
        // Allow empty blocks...
        List<ParameterAST> params;
        if (!getNextToken().equals("}")) {
            tokenizer.pushBack();
            params = parseParameters();
            expect("}");
        }
        else {
            params = new ArrayList<ParameterAST>();
        }
        
        return new BeanAST(beanName, context, extendsContext, params);
    }
    
    private List<ParameterAST> parseParameters()
    {
        List<ParameterAST> params = new ArrayList<ParameterAST>();
        while (true) {
            params.add( parseParameter() );
            
            String nextToken = getNextToken();
            tokenizer.pushBack();
            
            if (nextToken.equals("}")) {
                break;
            }
        }
        
        return params;
    }
    
    private ParameterAST parseParameter()
    {
        String paramName = getNextToken();
        expect(":");
        
        List<ParameterValueAST> values = parseParameterValues();

        String nextToken = getNextToken();
        if (!nextToken.equals(";")) {
            if (nextToken.equals("}")) {
                tokenizer.pushBack();
            }
            else {
                throw generateExpectedError(";' or '}");
            }
        }
        
        return new ParameterAST(paramName, values);
    }

    private List<ParameterValueAST> parseParameterValues()
    {
        List<ParameterValueAST> paramValues = new ArrayList<ParameterValueAST>();
        do {
            paramValues.add( parseParameterValue() );
        }
        while (getNextToken().equals(","));
     
        tokenizer.pushBack();
        return paramValues;
    }
    
    private ParameterValueAST parseParameterValue()
    {
        ParameterValueAST param = new ParameterValueAST(getNextToken());
        String nextToken = getNextToken();
        // Start of parameters for value?
        if (nextToken.equals("{")) {
            // Allow empty blocks...
            if (!getNextToken().equals("}")) {
                tokenizer.pushBack();
                param.setParameters( parseParameters() );
                expect("}");
            }
        }
        else {
            tokenizer.pushBack();
        }
        
        return param;
    }
}

