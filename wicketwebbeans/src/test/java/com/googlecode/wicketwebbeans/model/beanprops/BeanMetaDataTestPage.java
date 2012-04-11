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
package com.googlecode.wicketwebbeans.model.beanprops;


import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;

public class BeanMetaDataTestPage extends WebPage
{
    private static final long serialVersionUID = 1188462014965917173L;

    public BeanMetaDataTestPage()
    {
    }
    
    public void save(AjaxRequestTarget target, Form form, BeanMetaDataTestBean bean)
    {
    }

    public void cancel(AjaxRequestTarget target, Form form, BeanMetaDataTestBean bean)
    {
    }

    public void deleteRow(AjaxRequestTarget target, Form form, BeanMetaDataTestBean2 rowBean)
    {
    }

    public void addRow(AjaxRequestTarget target, Form form, BeanMetaDataTestBean rowBean)
    {
    }

    // Test generic Object
    public void doIt(AjaxRequestTarget target, Form form, Object rowBean)
    {
    }
}