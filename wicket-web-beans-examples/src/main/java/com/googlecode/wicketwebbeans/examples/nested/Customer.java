package com.googlecode.wicketwebbeans.examples.nested;

import java.io.Serializable;

public class Customer implements Serializable 
{
    private static final long serialVersionUID = -8500883418534059147L;

    private String firstName;
    private String lastName;
    private Address billToAddress;
    private Address shipToAddress;

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

    public Address getBillToAddress()
    {
        return billToAddress;
    }

    public void setBillToAddress(Address billToAddress)
    {
        this.billToAddress = billToAddress;
    }

    public Address getShipToAddress()
    {
        return shipToAddress;
    }

    public void setShipToAddress(Address shipToAddress)
    {
        this.shipToAddress = shipToAddress;
    }
}