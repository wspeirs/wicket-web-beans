package com.googlecode.wicketwebbeans.examples.api;

import java.io.Serializable;

public class TestBean implements Serializable 
{
    private static final long serialVersionUID = 4911652973164473144L;

    private String firstName;
    private String lastName;
    private Integer idNumber;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    
    public TestBean()
    {
    }

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

    public Integer getIdNumber()
    {
        return idNumber;
    }

    public void setIdNumber(Integer idNumber)
    {
        this.idNumber = idNumber;
    }

    public String getAddress1()
    {
        return address1;
    }

    public void setAddress1(String address1)
    {
        this.address1 = address1;
    }

    public String getAddress2()
    {
        return address2;
    }

    public void setAddress2(String address2)
    {
        this.address2 = address2;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }


}