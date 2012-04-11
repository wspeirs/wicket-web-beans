package com.googlecode.wicketwebbeans.examples.annotations;

import static com.googlecode.wicketwebbeans.annotations.Property.EMPTY;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicketwebbeans.annotations.Action;
import com.googlecode.wicketwebbeans.annotations.Bean;
import com.googlecode.wicketwebbeans.annotations.Property;
import com.googlecode.wicketwebbeans.annotations.Tab;
import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.model.BeanMetaData;

@Bean(type = TestBean.class, 
    tabs = {
        @Tab(name = "General", propertyNames = { "firstName", "lastName", "idNumber" }),
        @Tab(name = "Address", propertyNames = { 
            "address1", EMPTY, EMPTY, 
            "address2", EMPTY, EMPTY, "city", "state", "zip" })
    },
    // Customize certain properties from above.
    properties = {
      @Property(name = "firstName", required = true, maxLength = 10),
      @Property(name = "lastName", required = true)
    }
)

public class AnnotationsOnPage extends WebPage
{
    private static final long serialVersionUID = 2192855853289142345L;

    public AnnotationsOnPage()
    {
        TestBean bean = new TestBean();
        BeanMetaData meta = new BeanMetaData(bean.getClass(), null, this, null, false);
        add( new BeanForm("beanForm", bean, meta) );
    }

    @Action(confirm = "Are you sure you want to save?")
    public void save(AjaxRequestTarget target, Form form, TestBean bean)
    {
        
        if (!BeanForm.findBeanFormParent(form).validateRequired()) {
            return; // Errors
        }
        
        info("Saved");
    }
}
