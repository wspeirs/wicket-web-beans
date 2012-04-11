package com.googlecode.wicketwebbeans.examples.customfields;


import org.apache.wicket.markup.html.WebPage;

import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.model.BeanMetaData;
import com.googlecode.wicketwebbeans.model.ComponentRegistry;

public class CustomFieldPage extends WebPage
{
    private static final long serialVersionUID = 1092855853289323120L;

    public CustomFieldPage()
    {
        Address bean = new Address();
        
        // Register the ModelField for the Country class.
        ComponentRegistry registry = new ComponentRegistry();
        registry.register(Country.class, CountryField.class);
        
        BeanMetaData meta = new BeanMetaData(bean.getClass(), null, this, registry, false);
        add( new BeanForm("beanForm", bean, meta) );
    }
}
