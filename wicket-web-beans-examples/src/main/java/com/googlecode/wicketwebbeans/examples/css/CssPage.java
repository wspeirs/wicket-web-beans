package com.googlecode.wicketwebbeans.examples.css;


import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.model.BeanMetaData;
import com.googlecode.wicketwebbeans.model.ElementMetaData;
import com.googlecode.wicketwebbeans.model.api.JBean;
import com.googlecode.wicketwebbeans.model.api.JBeans;
import com.googlecode.wicketwebbeans.model.api.JProperty;


public class CssPage extends WebPage
{
    private static final long serialVersionUID = 2152855853285341120L;

    public CssPage()
    {
        TestBean bean = new TestBean();
        
        // Create the meta data
        JBeans jbeans = new JBeans(
            new JBean(TestBean.class)
                .columns(1)
                .css("greenBeanBorder")
                .propertyNames("firstName", "lastName", "rows")
                .properties( new JProperty("firstName").css("purpleColor") ),
            new JBean(RowBean.class)
                .css("redBeanBorder") 
                .dynamicCss("getRowCss")
                .properties( new JProperty("description").dynamicCss("getDescriptionCss") )
        );
        
        BeanMetaData meta = new BeanMetaData(bean.getClass(), null, jbeans, this, null, false);
        add( new BeanForm("beanForm", bean, meta) );
    }

    public void save(AjaxRequestTarget target, Form form, TestBean bean)
    {
        info("Saved");
    }
    
    public String getRowCss(RowBean bean, BeanMetaData beanMetaData)
    {
        if (bean.getAmount() > 0) {
            return "greaterThanZero";
        }
        
        if (bean.getAmount() < 0) {
            return "lessThanZero";
        }
        
        return null;
    }

    public String getDescriptionCss(RowBean bean, ElementMetaData elementMetaData)
    {
        if (bean.getAmount() > 0) {
            return "fieldPositive";
        }
        
        if (bean.getAmount() < 0) {
            return "fieldNegative";
        }
        
        return null;
    }
}
