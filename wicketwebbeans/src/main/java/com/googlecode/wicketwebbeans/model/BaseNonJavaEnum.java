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
import java.util.List;

import com.googlecode.wicketwebbeans.model.NonJavaEnum;


/**
 * Base implementation of NonJavaEnum to make it look similar to a Java Enum.
 * 
 * @author Marc Stock
 * @author Dan Syrstad
 */
public abstract class BaseNonJavaEnum implements NonJavaEnum, Serializable
{
    protected String name;
    protected String displayValue;

    public BaseNonJavaEnum(String name, String displayValue)
    {
        this.name = name;
        this.displayValue = displayValue;
    }

    public String name()
    {
        return name;
    }

    public String getDisplayValue()
    {
        return displayValue;
    }

    public void setDisplayValue(String displayValue)
    {
        this.displayValue = displayValue;
    }

    /**
     * Get the Enum for the given name.
     *
     * @param enumValue name to match
     *
     * @return a Enum, or null if not found.
     * @param enums cachedEnums to search through
     */
    public static BaseNonJavaEnum valueOf(String enumValue, List<? extends BaseNonJavaEnum> enums)
    {
        if (enumValue == null) {
            return null;
        }
        for (BaseNonJavaEnum nonJavaEnum : enums) {
            if (nonJavaEnum.name().equals(enumValue)) {
                return nonJavaEnum;
            }
        }

        return null;
    }

    @Override
    public String toString()
    {
        return getDisplayValue();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof BaseNonJavaEnum)) {
            return false;
        }

        BaseNonJavaEnum other = (BaseNonJavaEnum)obj;
        return name().equals(other.name());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 59 * hash + (this.displayValue != null ? this.displayValue.hashCode() : 0);
        return hash;
    }
}
