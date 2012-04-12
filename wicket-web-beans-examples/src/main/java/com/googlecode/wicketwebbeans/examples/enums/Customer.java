package com.googlecode.wicketwebbeans.examples.enums;

import java.io.Serializable;

public class Customer implements Serializable 
{
    private static final long serialVersionUID = -8500883418534059147L;

    private String firstName;
    private String lastName;
    private CustomerType customerType;

    public Customer()
    {
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = (firstName == null ? null : firstName.toUpperCase());
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public CustomerType getCustomerType()
    {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType)
    {
        this.customerType = customerType;
    }
}