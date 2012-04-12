package com.googlecode.wicketwebbeans.examples.models;


import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;

import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.examples.LoadableDetachableObjectModel;
import com.googlecode.wicketwebbeans.model.BeanMetaData;

public class ModelBeanPage extends WebPage
{
    private static final long serialVersionUID = 2219855853289144320L;

    public ModelBeanPage()
    {
        IModel beanModel = new LoadableDetachableObjectModel();
        
        BeanMetaData meta = new BeanMetaData(beanModel.getObject().getClass(), null, this, null, false);
        add( new BeanForm("beanForm", beanModel, meta) );
    }
}
