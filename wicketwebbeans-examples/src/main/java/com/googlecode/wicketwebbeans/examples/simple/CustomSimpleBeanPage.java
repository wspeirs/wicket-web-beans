package com.googlecode.wicketwebbeans.examples.simple;


import org.apache.wicket.markup.html.WebPage;

import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.model.BeanMetaData;

public class CustomSimpleBeanPage extends WebPage
{
    private static final long serialVersionUID = 2142855853289519120L;

    public CustomSimpleBeanPage()
    {
        TestBean bean = new TestBean();
        BeanMetaData meta = new BeanMetaData(bean.getClass(), null, this, null, false);
        add( new BeanForm("beanForm", bean, meta) );
    }
}
