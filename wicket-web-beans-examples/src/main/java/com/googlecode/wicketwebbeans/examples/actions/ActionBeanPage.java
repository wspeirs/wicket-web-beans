package com.googlecode.wicketwebbeans.examples.actions;


import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.examples.simple.TestBean;
import com.googlecode.wicketwebbeans.model.BeanMetaData;

public class ActionBeanPage extends WebPage
{
    private static final long serialVersionUID = 1982855853289142640L;

    public ActionBeanPage()
    {
        TestBean bean = new TestBean();
        BeanMetaData meta = new BeanMetaData(bean.getClass(), null, this, null, false);
        add( new BeanForm("beanForm", bean, meta) );
    }

    public void save(AjaxRequestTarget target, Form form, TestBean bean)
    {
        info("Saved - thank you");
    }

    public void cancel(AjaxRequestTarget target, Form form, TestBean bean)
    {
        info("Canceled - thank you");
    }

    public void clearLastName(AjaxRequestTarget target, Form form, TestBean bean)
    {
        bean.setLastName("");
    }
}
