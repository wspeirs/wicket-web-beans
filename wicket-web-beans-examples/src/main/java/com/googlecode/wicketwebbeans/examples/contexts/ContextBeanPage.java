package com.googlecode.wicketwebbeans.examples.contexts;

import java.math.BigDecimal;


import org.apache.wicket.markup.html.WebPage;

import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.examples.simple.TestBean;
import com.googlecode.wicketwebbeans.model.BeanMetaData;

public class ContextBeanPage extends WebPage
{
    private static final long serialVersionUID = 1182855853289191460L;

    public ContextBeanPage()
    {
        TestBean bean = new TestBean();
        bean.setFirstName("Dan");
        bean.setLastName("Syrstad");
        bean.setOperand1(BigDecimal.valueOf(123.0));
        bean.setOperand2(BigDecimal.valueOf(456.0));
        
        BeanMetaData meta = new BeanMetaData(bean.getClass(), "limitedEdit", this, null, false);
        add( new BeanForm("beanForm", bean, meta) );
    }
}
