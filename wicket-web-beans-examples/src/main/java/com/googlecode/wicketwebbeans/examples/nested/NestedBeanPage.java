package com.googlecode.wicketwebbeans.examples.nested;


import org.apache.wicket.markup.html.WebPage;

import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.model.BeanMetaData;

public class NestedBeanPage extends WebPage
{
    private static final long serialVersionUID = 1842855853289143920L;

    public NestedBeanPage()
    {
        Customer bean = new Customer();
        BeanMetaData meta = new BeanMetaData(bean.getClass(), null, this, null, false);
        add( new BeanForm("beanForm", bean, meta) );
    }
}
