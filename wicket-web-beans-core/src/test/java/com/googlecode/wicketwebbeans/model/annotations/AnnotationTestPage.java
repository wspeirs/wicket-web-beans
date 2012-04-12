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
package com.googlecode.wicketwebbeans.model.annotations;

import static com.googlecode.wicketwebbeans.annotations.Property.EMPTY;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicketwebbeans.annotations.Action;
import com.googlecode.wicketwebbeans.annotations.Bean;
import com.googlecode.wicketwebbeans.annotations.Beans;
import com.googlecode.wicketwebbeans.annotations.Property;
import com.googlecode.wicketwebbeans.annotations.Tab;
import com.googlecode.wicketwebbeans.fields.BeanGridField;
import com.googlecode.wicketwebbeans.fields.BeanInlineField;
import com.googlecode.wicketwebbeans.fields.TextAreaField;

@Beans({
    @Bean(type = AnnotationTestBean.class, label = "My ${TestBean.title} Title",
          propertyNames = { "action.save", "firstName", "lastName",
            EMPTY, "activePrimitive", "color", "inlineBean",
            "dateTimestamp", "blockBean", "testBean2", "popupBean",
            "-subComponent" },
          tabs = {
            @Tab(name = "nameInfo"),
            @Tab(name = "miscInfo", label = "${MiscInfo.title}", 
                 propertyNames = { "gender", EMPTY, "age", "operand1", "operand2",
                    "result", "palette", "palette2", "description" 
                 }),
            @Tab(name = "listOfBeans", propertyNames = { "action.addRow", "beans" }) 
          },
          properties = {
            @Property(name = "inlineBean", fieldType = BeanInlineField.class, colspan = 3),
            @Property(name = "blockBean", fieldType = BeanGridField.class, colspan = 3),
            @Property(name = "testBean2", colspan = 3),
            @Property(name = "popupBean", colspan = 3),
            @Property(name = "result", viewOnly = true),
            @Property(name = "palette", elementType =  AnnotationTestBean.ColorEnum.class),
            @Property(name = "palette2", elementType =  AnnotationTestBean.ColorEnum.class, viewOnly = true),
            @Property(name = "description", fieldType = TextAreaField.class, rows = 5),
            @Property(name = "beans", viewOnly = true, rows = 20)
          },
          actions = {
            @Action(name = "save", colspan = 3),
            @Action(name = "addRow", colspan = 3)
          }
    ),
     
    @Bean(type = AnnotationTestBean.class, context = "view", 
          label = "Bean View", viewOnly = true,
          properties = {
            @Property(name = "action.save", colspan = 4),
            @Property(name = "firstName", viewOnly = false)
          },
          tabs = @Tab(name = "miscInfo", propertyNames = "-palette2")
    ),
     
    @Bean(type = AnnotationTestBean2.class, actionNames = "-doIt",
          properties = {
            @Property(name = "action.deleteRow", labelImage = "remove.gif"),
            @Property(name = "selected", label = "X"),
            @Property(name = "firstName", viewOnly = false),
            @Property(name = "lastName")         
          }
    )
})
public class AnnotationTestPage extends WebPage
{
    public AnnotationTestPage()
    {
    }
    
    @Action(ajax = true)
    public void save(AjaxRequestTarget target, Form form, AnnotationTestBean bean)
    {
    }

    public void cancel(AjaxRequestTarget target, Form form, AnnotationTestBean bean)
    {
    }

    public void deleteRow(AjaxRequestTarget target, Form form, AnnotationTestBean2 rowBean)
    {
    }

    public void addRow(AjaxRequestTarget target, Form form, AnnotationTestBean rowBean)
    {
    }

    // Test generic Object
    public void doIt(AjaxRequestTarget target, Form form, Object rowBean)
    {
    }
}