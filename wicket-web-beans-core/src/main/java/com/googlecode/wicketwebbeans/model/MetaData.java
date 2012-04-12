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

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import org.apache.wicket.Component;
import org.apache.wicket.util.string.Strings;

import com.googlecode.wicketwebbeans.util.WicketUtil;

/**
 * Common metadata methods. <p>
 * 
 * @author Dan Syrstad
 */
public class MetaData implements Serializable
{
    private static final long serialVersionUID = 2472855853289142315L;

    private Map<String, String[]> parameters = new HashMap<String, String[]>();
    private Set<String> consumedParameters = new HashSet<String>();
    private Component component;

    /**
     * Construct a MetaData. 
     * 
     * @param component the component to use for macro localization.
     */
    public MetaData(Component component)
    {
        this.component = component;
    }

    /**
     * Consumes a parameter.
     *
     * @param param the parameter to consume.
     */
    public void consumeParameter(String param)
    {
        consumedParameters.add(param);
    }

    /**
     * @param unconsumedMsgs messages that report the parameter keys that were specified but not consumed.
     * @param context a context for the unconsumed message.
     * @return true if all parameters specified have been consumed.
     */
    public boolean areAllParametersConsumed(String context, Set<String> unconsumedMsgs)
    {
        boolean result = true;
        for (Object parameter : parameters.keySet()) {
            if (!consumedParameters.contains(parameter)) {
                unconsumedMsgs.add(context+ ": Parameter " + parameter + " was not consumed");
                result = false;
            }
        }
        
        return result;
    }

    /**
     * Gets the specified parameter. If the parameter has multiple values, the first value is returned.
     *
     * @param key the parameter key.
     * 
     * @return the parameter value, or null if not set.
     */
    public String getParameter(String key)
    {
        consumeParameter(key);
        String[] values = parameters.get(key);
        if (values == null || values.length == 0) {
            return null;
        }
        
        return values[0];
    }
    
    /**
     * Gets the specified parameter. If the parameter has multiple values, the first value is returned.
     *
     * @param key the parameter key.
     * @param defaultValue the default value to be returned if the parameter value is null.
     * 
     * @return the parameter value, or defaultValue if not set.
     */
    public String getParameter(String key, String defaultValue)
    {
        String value = getParameter(key);
        return value == null ? defaultValue : value;
    }
    
    /**
     * Gets the specified parameter as a multi-valued array.
     *
     * @param key the parameter key.
     * 
     * @return the parameter values, or null if not set.
     */
    public String[] getParameterValues(String key)
    {
        consumeParameter(key);
        return parameters.get(key);
    }
    
    /**
     * Gets a boolean parameter.
     *
     * @param key the parameter key.
     * 
     * @return true or false. False is returned if the parameter is not set.
     */
    public boolean getBooleanParameter(String key)
    {
        return Boolean.valueOf( getParameter(key, "false") );
    }
    
    /**
     * Gets an Integer parameter.
     *
     * @param key the parameter key.
     * 
     * @return the value, or null if not set.
     */
    public Integer getIntegerParameter(String key)
    {
        String value = getParameter(key);
        return value == null ? null : Integer.valueOf(value);
    }
    
    /**
     * Gets an int parameter.
     *
     * @param key the parameter key.
     * @param defaultValue
     * @return the value, or defaultValue if not set.
     */
    public int getIntParameter(String key, int defaultValue)
    {
        Integer value = getIntegerParameter(key);
        return value == null ? defaultValue : value;
    }
    
    /**
     * Sets a parameter.
     *
     * @param key the parameter key.
     * @param value the parameter value. Macros ("${...}")) in this value will be substituted from the component's localizer.
     */
    public void setParameter(String key, String value)
    {
        parameters.put(key, new String[] { WicketUtil.substituteMacros(value, component) });
    }
    
    /**
     * Sets a parameter that has multiple values. 
     *
     * @param key the parameter key.
     * 
     * @param values the parameter values. Macros ("${...}")) in these values will be substituted from the component's localizer.
     */
    public void setParameterValues(String key, String[] values)
    {
        for (int i = 0; i < values.length; i++) {
            values[i] = WicketUtil.substituteMacros(values[i], component);
        }
        
        parameters.put(key, values);
    }
    
    /**
     * Sets a parameter if the value is not empty or null.
     *
     * @param key the parameter key. If empty or null, the parameter is not set.
     * @param value the parameter value. If empty or null, the parameter is not set.
     */
    public void setParameterIfNotEmpty(String key, String value)
    {
        if (!Strings.isEmpty(key) && !Strings.isEmpty(value)) {
            setParameter(key, value);
        }
    }
    
    /**
     * Sets a parameter if the values array is not empty or null.
     *
     * @param key the parameter key. If empty or null, the parameter is not set.
     * @param values the parameter values. If empty or null, the parameter is not set.
     */
    public void setParameterIfNotEmpty(String key, String[] values)
    {
        if (!Strings.isEmpty(key) && values != null && values.length > 0) {
            setParameterValues(key, values);
        }
    }
    
    /**
     * Gets the parameters.
     *
     * @return the parameters.
     */
    public Map<String, String[]> getParameters()
    {
        return parameters;
    }
    
    /**
     * Sets the parameters.
     *
     * @param parameters the parameters to set.
     */
    public void setParameters(Map<String, String[]> parameters)
    {
        this.parameters = parameters;
    }

}
