package com.googlecode.wicketwebbeans.examples.complex;

import java.io.Serializable;

import com.googlecode.wicketwebbeans.annotations.Property;


public class TwoFormsBean implements Serializable 
{
    private static final long serialVersionUID = -8500883418534059147L;

    private String firstName;
    private String lastName;
    private Integer number;

    public TwoFormsBean()
    {
    }

    @Property(required = true)
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Integer getNumber()
    {
        return number;
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }
}