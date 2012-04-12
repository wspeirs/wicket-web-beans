package com.googlecode.wicketwebbeans.databinder.examples;

import com.googlecode.wicketwebbeans.databinder.DataBeanEditPanel;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;

public class EditPage extends WebPage
{
    private static final long serialVersionUID = 3012855853286535126L;

    public EditPage(Object bean, Page returnPage)
    {
        add(new EditPanel("beanForm", bean, returnPage));
    }


    public static class EditPanel extends DataBeanEditPanel
    {
        private static final long serialVersionUID = 1132855853286535598L;

        public EditPanel(String id, Object bean, Page returnPage)
        {
            super(id, bean, returnPage);
        }
    }
}
