package com.googlecode.wicketwebbeans.examples.yuidate;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


import org.apache.wicket.markup.html.WebPage;

import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.fields.YUIDateField;
import com.googlecode.wicketwebbeans.model.BeanMetaData;
import com.googlecode.wicketwebbeans.model.ComponentRegistry;


public class DatePage extends WebPage
{
    private static final long serialVersionUID = 2572855853286541120L;

    public DatePage()
    {
        ComponentRegistry registry = new ComponentRegistry();
        registry.register(Date.class, YUIDateField.class);
        registry.register(java.sql.Date.class, YUIDateField.class);
        registry.register(Time.class, YUIDateField.class);
        registry.register(Timestamp.class, YUIDateField.class);
        //registry.register(Calendar.class, YUIDateField.class);

        TestBean bean = new TestBean();
        BeanMetaData meta = new BeanMetaData(bean.getClass(), null, this, registry, false);
        add( new BeanForm("beanForm", bean, meta) );
    }
}
