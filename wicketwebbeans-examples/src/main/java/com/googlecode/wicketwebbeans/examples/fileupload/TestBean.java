package com.googlecode.wicketwebbeans.examples.fileupload;

import java.io.Serializable;
import java.util.logging.Logger;

import com.googlecode.wicketwebbeans.annotations.Property;
import com.googlecode.wicketwebbeans.fields.FileUploaderField;



public class TestBean implements Serializable 
{
    private static final long serialVersionUID = 4911652973164473144L;

    private String documentName;
    
    public TestBean()
    {
    }

    public String getDocumentName()
    {
        return documentName;
    }

    public void setDocumentName(String documentName)
    {
        this.documentName = documentName;
    }

    @Property(fieldType = FileUploaderField.class)
    public String getFileName1()
    {
        return "";
    }

    public void setFileName1(String fileName)
    {
        Logger.getAnonymousLogger().info("Got fileName1=" + fileName);
    }

    @Property(fieldType = FileUploaderField.class)
    public String getFileName2()
    {
        return "Stuff";
    }

    public void setFileName2(String fileName)
    {
        Logger.getAnonymousLogger().info("Got fileName1=" + fileName);
    }

}