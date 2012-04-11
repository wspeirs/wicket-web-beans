package com.googlecode.wicketwebbeans.examples.container;


import org.apache.wicket.markup.html.WebPage;

import com.googlecode.wicketwebbeans.annotations.Bean;
import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.containers.VerticalLayoutBeanPanel;
import com.googlecode.wicketwebbeans.model.BeanMetaData;


@Bean(type = TestBean.class, container = VerticalLayoutBeanPanel.class)
public class CustomContainerPage extends WebPage
{
    private static final long serialVersionUID = 1209855853289131570L;

    public CustomContainerPage()
    {
        TestBean bean = new TestBean();
        BeanMetaData meta = new BeanMetaData(bean.getClass(), null, this, null, false);
        add( new BeanForm("beanForm", bean, meta) );
    }
}
