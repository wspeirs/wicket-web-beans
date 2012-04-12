package com.googlecode.wicketwebbeans.databinder.examples;


import org.apache.wicket.model.IModel;
import net.databinder.models.hib.*;
import com.googlecode.wicketwebbeans.fields.DropDownChoiceField;
import com.googlecode.wicketwebbeans.model.ElementMetaData;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class CategoryField extends DropDownChoiceField
{
    private static final long serialVersionUID = 3522855853286535180L;

    public CategoryField(String id, IModel model, ElementMetaData metaData, boolean viewOnly)
    {
        super(id, model, metaData, viewOnly, new HibernateListModel("from Category order by name"), new ChoiceRenderer("name"));
    }
}
