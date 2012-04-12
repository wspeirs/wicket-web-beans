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
package com.googlecode.wicketwebbeans.util;

import org.apache.wicket.Component;
import org.apache.wicket.Localizer;


/**
 * Static wicket utilities.
 * 
 * @author Dan Syrstad
 */
public class WicketUtil
{
    private WicketUtil() { }
    
    /**
     * Substitutes macros of the form "${key}" in string to the corresponding value of
     * "key" in localizer. 
     *
     * @param string the input string containing the macros.
     * @param component the component whose Localizer is used to localize the string.
     * 
     * @return a String with the macros expanded. If a macro key cannot be found in 
     *  localizer, the macro is substituted with "?string?".
     */
    public static String substituteMacros(String string, Component component) 
    {
        Localizer localizer = component.getLocalizer();
        StringBuilder buf = new StringBuilder(string);
        int idx = 0;
        while ((idx = buf.indexOf("${", idx)) >= 0) {
            int endIdx = buf.indexOf("}", idx + 2);
            if (endIdx > 0) {
                String key = buf.substring(idx + 2, endIdx);
                String value = localizer.getString(key, component, '?' + key + '?');
                if (value != null) {
                    buf.replace(idx, endIdx + 1, value);
                }
                else {
                    // Nothing found, skip macro.
                    idx += 2;
                }
            }
        }
        
        return buf.toString();
    }
}
