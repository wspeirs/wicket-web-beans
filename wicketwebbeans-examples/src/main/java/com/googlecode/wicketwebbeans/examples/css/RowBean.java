package com.googlecode.wicketwebbeans.examples.css;

import java.io.Serializable;


public class RowBean implements Serializable
{
    private static final long serialVersionUID = 3142855853289142316L;

    private String description;
    private int amount;
    
    public RowBean()
    {
    }

    public RowBean(String description, int amount)
    {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

}
