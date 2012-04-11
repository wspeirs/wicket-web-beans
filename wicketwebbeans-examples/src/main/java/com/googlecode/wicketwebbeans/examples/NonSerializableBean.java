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

package com.googlecode.wicketwebbeans.examples;


/**
 * A test bean that is not Serializable. <p>
 * 
 * @author Dan Syrstad
 */
public class NonSerializableBean
{
    private String name;
    private String serialNumber;
    
    /**
     * Construct a NonSerializableBean. 
     *
     * @param name
     * @param serialNumber
     */
    public NonSerializableBean(String name, String serialNumber)
    {
        this.name = name;
        this.serialNumber = serialNumber;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }
}
