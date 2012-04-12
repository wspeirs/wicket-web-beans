package com.googlecode.wicketwebbeans.examples.complex;



import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.model.BeanMetaData;

public class TwoFormsPage extends WebPage
{
    private static final long serialVersionUID = 1L;

    public TwoFormsPage()
    {
        TwoFormsBean bean1 = new TwoFormsBean();
        BeanMetaData meta1 = new BeanMetaData(bean1.getClass(), null, this, null, false);
        BeanForm beanForm1 = new BeanForm("beanForm1", bean1, meta1);
        add(beanForm1);

        TwoFormsBean bean2 = new TwoFormsBean();
        BeanMetaData meta2 = new BeanMetaData(bean2.getClass(), null, this, null, false);
        BeanForm beanForm2 = new BeanForm("beanForm2", bean2, meta2);
        add(beanForm2);
    }
    
    public void save(AjaxRequestTarget target, Form form, TwoFormsBean bean)
    {
        BeanForm beanForm = BeanForm.findBeanFormParent(form);
        if (!beanForm.validateRequired()) {
            return; // Errors
        }
        
        beanForm.info("Saved - thank you");
    }

    public void cancel(AjaxRequestTarget target, Form form, TwoFormsBean bean)
    {
        BeanForm beanForm = BeanForm.findBeanFormParent(form);
        info("Canceled - Page message");
        beanForm.info("Canceled - Form message");
    }
}