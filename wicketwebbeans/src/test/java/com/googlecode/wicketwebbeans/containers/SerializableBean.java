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

package com.googlecode.wicketwebbeans.containers;

import java.io.Serializable;

/**
 * A test bean that is Serializable. <p>
 * 
 * @author Dan Syrstad
 */
public class SerializableBean extends NonSerializableBean implements Serializable
{
    private static final long serialVersionUID = 1128469813614931174L;

    /**
     * Construct a SerializableBean. 
     *
     * @param name
     * @param serialNumber
     */
    public SerializableBean(String name, String serialNumber)
    {
        super(name, serialNumber);
    }

}
