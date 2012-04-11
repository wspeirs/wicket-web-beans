package com.googlecode.wicketwebbeans.examples.annotations;


import org.apache.wicket.ajax.AjaxRequestTarget;


import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicketwebbeans.annotations.Action;
import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.model.BeanMetaData;

public class AnnotationsOnBeanPage extends WebPage
{
    private static final long serialVersionUID = 1072855853289135160L;

    public AnnotationsOnBeanPage()
    {
        TestBeanWithAnnotations bean = new TestBeanWithAnnotations();
        BeanMetaData meta = new BeanMetaData(bean.getClass(), "someContext", this, null, false);
        add( new BeanForm("beanForm", bean, meta) );
    }

    @Action(confirm = "Are you sure you want to save?")
    public void save(AjaxRequestTarget target, Form form, TestBeanWithAnnotations bean)
    {
        
        if (!BeanForm.findBeanFormParent(form).validateRequired()) {
            return; // Errors
        }
        
        info("Saved");
    }
}
