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

import java.util.ArrayList;
import java.util.List;


/**
 * ParameterValue AST for BeanPropsParser. <p>
 * 
 * @author Dan Syrstad 
 */
public class ParameterValueAST
{
    private String value;
    private List<ParameterAST> parameters = new ArrayList<ParameterAST>();

    public ParameterValueAST(String value)
    {
        this.value = value;
    }

    public List<ParameterAST> getParameters()
    {
        return parameters;
    }

    public void setParameters(List<ParameterAST> parameters)
    {
        this.parameters = parameters;
    }

    /**
     * @return the raw value without substitution of macros.
     */
    public String getValue()
    {
        return value;
    }
}
