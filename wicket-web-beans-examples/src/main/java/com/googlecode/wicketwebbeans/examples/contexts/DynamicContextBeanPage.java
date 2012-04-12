package com.googlecode.wicketwebbeans.examples.contexts;


import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicketwebbeans.containers.BeanForm;
import com.googlecode.wicketwebbeans.examples.simple.TestBean;
import com.googlecode.wicketwebbeans.model.BeanMetaData;
import com.googlecode.wicketwebbeans.model.api.JAction;
import com.googlecode.wicketwebbeans.model.api.JBean;
import com.googlecode.wicketwebbeans.model.api.JBeans;
import com.googlecode.wicketwebbeans.model.api.JProperty;

/**
 * NOTE: to use action methods as global actions or without an ajax context,
 * please use:
 *     setResponsePage(DynamicContextBeanPage.class);
 * otherwise, in ajax context use:
 *     target.addComponent(this.form);
 */
public class DynamicContextBeanPage extends WebPage
{
    private static final long serialVersionUID = 1002855853289127918L;

    JBeans jbeans;
    BeanMetaData meta;
    BeanForm form;
    private boolean global = false;

    private static TestBean bean;

    static {
        if (bean == null) {
            bean = new TestBean(); // Think of this as getting it from DB
        }
    }

    public DynamicContextBeanPage()
    {
        /**
         * NOTE: you take actions away from beans metadata
         * and test the contexts with global actions
         */
        jbeans = new JBeans(
            new JBean(TestBean.class)
                .context("nofirstname")
                .actions(
                    new JAction("goToGlobal").ajax(true)
                )
                .properties(
                    new JProperty("action.goToGlobal"),
                    new JProperty("-action.clear"),
                    new JProperty("-action.global"),
                    new JProperty("firstName"),
                    new JProperty("lastName"),
                    new JProperty("operand1"),
                    new JProperty("operand2"),
                    new JProperty("result"),
                    new JProperty("number")
                ),
            new JBean(TestBean.class)
                .context("clearfirstname")
                .actions(
                    new JAction("clear").ajax(true)
                )
                .properties(
                    new JProperty("-action.goToGlobal"),
                    new JProperty("action.clear"),
                    new JProperty("-action.global"),
                    new JProperty("firstName").viewOnly(true),
                    new JProperty("lastName").viewOnly(true),
                    new JProperty("operand1"),
                    new JProperty("operand2"),
                    new JProperty("-result"),
                    new JProperty("-number")
                ),

            new JBean(TestBean.class)
                .context("global")
                .properties(
                    new JProperty("-action.goToGlobal"),
                    new JProperty("-action.clear"),
                    new JProperty("firstName"),
                    new JProperty("lastName").viewOnly(true),
                    new JProperty("operand1").viewOnly(true),
                    new JProperty("operand2").viewOnly(true),
                    new JProperty("result").viewOnly(true),
                    new JProperty("number").viewOnly(true)
                )

        );
        meta = new BeanMetaData(bean.getClass(), getContext(), jbeans, this, null, false);
        form = new BeanForm("beanForm", bean, meta);
        form.setOutputMarkupId(true);
        add( form );
    }

    private String getContext() {
        if (global) {
            return "global";
        } else {
            if (bean.getFirstName() == null || bean.getFirstName().equals("")) {
                return "nofirstname";
            } else {
                return "clearfirstname";
            }
        }
    }

    /**
     * Actions
     * @param target
     * @param form
     * @param bean
     */
    public void goToGlobal(AjaxRequestTarget target, Form form, TestBean bean) {
        global = true;
        meta.setContext(getContext());
        this.form.recreateFormContents();
        target.addComponent(this.form);
    }

    public void clear(AjaxRequestTarget target, Form form, TestBean bean) {
        bean.setFirstName("");
        meta.setContext(getContext());
        this.form.recreateFormContents();
        target.addComponent(this.form);
    }

    public void global(AjaxRequestTarget target, Form form, TestBean bean) {
        global = false;
        meta.setContext(getContext());
        this.form.recreateFormContents();
        setResponsePage(DynamicContextBeanPage.class);
    }

}
