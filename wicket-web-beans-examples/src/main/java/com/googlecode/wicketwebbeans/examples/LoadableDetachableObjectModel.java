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

import org.apache.wicket.model.LoadableDetachableModel;

/**
 * A LoadableDetachableModel that returns a single bean that is not Serializable. <p>
 * 
 * @author Dan Syrstad
 */
public class LoadableDetachableObjectModel extends LoadableDetachableModel
{
    private static final long serialVersionUID = 4107532879860694959L;

    /**
     * Construct a LoadableDetachableObjectModel. 
     *
     */
    public LoadableDetachableObjectModel()
    {
    }

    /** 
     * {@inheritDoc}
     * @return 
     * @see org.apache.wicket.model.LoadableDetachableModel#load()
     */
    @Override
    protected Object load()
    {
        return new NonSerializableBean("ModelS2000", "1234567");
    }
}
