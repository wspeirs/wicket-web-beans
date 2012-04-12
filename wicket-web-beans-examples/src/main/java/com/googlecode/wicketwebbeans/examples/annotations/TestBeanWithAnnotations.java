package com.googlecode.wicketwebbeans.examples.annotations;

import static com.googlecode.wicketwebbeans.annotations.Property.EMPTY;

import java.io.Serializable;

import com.googlecode.wicketwebbeans.annotations.Bean;
import com.googlecode.wicketwebbeans.annotations.Beans;
import com.googlecode.wicketwebbeans.annotations.Parameter;
import com.googlecode.wicketwebbeans.annotations.Property;
import com.googlecode.wicketwebbeans.annotations.Tab;



@Beans({
    @Bean(
        tabs = {
            @Tab(name = "General", propertyNames = { "firstName", "lastName", "idNumber" }),
            @Tab(name = "Address", propertyNames = { 
                "address1", EMPTY, EMPTY, 
                "address2", EMPTY, EMPTY, "city", "state", "zip" })
        },
        params = @Parameter(name = "customParam", value = { "value1", "value2", "value3"})
    ),

    // Inherits from default context.
    @Bean(context = "someContext", propertyNames = "-idNumber")
})
public class TestBeanWithAnnotations implements Serializable
{
    private static final long serialVersionUID = 1149855853289142361L;

    private String firstName;
    private String lastName;
    private Integer idNumber;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    
    public TestBeanWithAnnotations() {  }

    @Property(required = true, maxLength = 10)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Property(required = true)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Integer idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}