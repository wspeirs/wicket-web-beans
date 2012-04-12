
package com.googlecode.wicketwebbeans.examples.complex;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TestBean2 implements Serializable
{
    private static final long serialVersionUID = -8500883418534059147L;
    public enum Gender { Male, Female };

    private String firstName;
    private String lastName;
    private Date startDate;
    private Integer age;
    private BigDecimal savingsAmount;
    private Gender gender;
    private Boolean selected;

    public TestBean2()
    {
    }

    public TestBean2(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * Gets the age.
     *
     * @return a Integer.
     */
    public Integer getAge()
    {
        return age;
    }
    /**
     * Sets age.
     *
     * @param age a Integer.
     */
    public void setAge(Integer age)
    {
        this.age = age;
    }
    /**
     * Gets the firstName.
     *
     * @return a String.
     */
    public String getFirstName()
    {
        return firstName;
    }
    /**
     * Sets firstName.
     *
     * @param firstName a String.
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    /**
     * Gets the lastName.
     *
     * @return a String.
     */
    public String getLastName()
    {
        return lastName;
    }
    /**
     * Sets lastName.
     *
     * @param lastName a String.
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    /**
     * Gets the savingsAmount.
     *
     * @return a BigDecimal.
     */
    public BigDecimal getSavingsAmount()
    {
        return savingsAmount;
    }
    /**
     * Sets savingsAmount.
     *
     * @param savingsAmount a BigDecimal.
     */
    public void setSavingsAmount(BigDecimal savingsAmount)
    {
        this.savingsAmount = savingsAmount;
    }
    /**
     * Gets the startDate.
     *
     * @return a Date.
     */
    public Date getStartDate()
    {
        return startDate;
    }
    /**
     * Sets startDate.
     *
     * @param startDate a Date.
     */
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    /**
     * Gets the gender.
     *
     * @return a Gender.
     */
    public Gender getGender()
    {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param gender a Gender.
     */
    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public Boolean getSelected()
    {
        return selected;
    }

    public void setSelected(Boolean selected)
    {
        this.selected = selected;
    }

}