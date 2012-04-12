package com.googlecode.wicketwebbeans.databinder.examples;

import com.googlecode.wicketwebbeans.databinder.DataBeanListPanel;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

public class ListPage extends WebPage
{
    private static final long serialVersionUID = 1112855853286535564L;

    public ListPage()
    {
        Form form = new Form("contactForm");
        add(form);
        form.add(new ListPanel("contactBeanTable", Contact.class, this));
        form = new Form("categoryForm");
        add(form);
        form.add(new ListPanel("categoryBeanTable", Category.class, this));

    }


    public static class ListPanel extends DataBeanListPanel
    {
        private static final long serialVersionUID = 3102855853286535541L;

        private Page returnPage;

        public ListPanel(String id, Class<?> beanClass, Page returnPage)
        {
            super(id, beanClass);
            this.returnPage = returnPage;
        }

        public void edit(AjaxRequestTarget target, Form form, Object bean)
        {
            setResponsePage(new EditPage(bean, returnPage));
        }
    }
}
