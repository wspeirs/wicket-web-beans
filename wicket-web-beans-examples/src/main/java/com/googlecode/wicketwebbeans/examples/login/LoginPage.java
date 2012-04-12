package com.googlecode.wicketwebbeans.examples.login;


import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.model.BeanMetaData;
import com.googlecode.wicketwebbeans.model.api.JBean;


public class LoginPage extends WebPage
{
    private static final long serialVersionUID = 1142855853289149320L;

    public LoginPage()
    {
        LoginBean bean = new LoginBean();
        
        // Create the meta data
        JBean jbean = new JBean(LoginBean.class)
            .propertyNames("userName", "password", "action.login");
        
        BeanMetaData meta = new BeanMetaData(bean.getClass(), null, jbean, this, null, false);
        add( new BeanForm("beanForm", bean, meta) );
    }

    public void login(AjaxRequestTarget target, Form form, LoginBean bean)
    {
        if (!BeanForm.findBeanFormParent(form).validateRequired()) {
            return; // Errors
        }
        
        if (bean.getUserName().equals("wicket") && bean.getPassword().equals("xyzzy")) {
            info("Logged In");
        }
        else {
            error("Invalid Username or Password - Please try again");
        }
    }
}
