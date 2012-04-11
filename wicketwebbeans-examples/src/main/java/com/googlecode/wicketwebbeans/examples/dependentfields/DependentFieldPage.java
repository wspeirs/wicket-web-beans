package com.googlecode.wicketwebbeans.examples.dependentfields;


import org.apache.wicket.markup.html.WebPage;

import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.model.BeanMetaData;
import com.googlecode.wicketwebbeans.model.ComponentRegistry;

public class DependentFieldPage extends WebPage
{
    private static final long serialVersionUID = 102855853289141014L;

    public DependentFieldPage()
    {
        // Register the ModelField for the Model enum class.
        ComponentRegistry registry = new ComponentRegistry();
        registry.register(Model.class, ModelField.class);
        
        Car bean = new Car();
        BeanMetaData meta = new BeanMetaData(bean.getClass(), null, this, registry, false);
        add( new BeanForm("beanForm", bean, meta) );
    }
}
