package com.googlecode.wicketwebbeans.examples.simple;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;

public class TestBean implements Serializable 
{
    private static final long serialVersionUID = -8500883418534059147L;

    private String firstName;
    private String lastName;
    private Integer number;
    private BigDecimal operand1;
    private BigDecimal operand2;
    
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public TestBean()
    {
    }

    /**
     * JavaBeans compliant method to add a PropertyChangeListener. 
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.addPropertyChangeListener(listener);
    }
    
    /**
     * JavaBeans compliant method to remove a PropertyChangeListener. 
     * @param listener 
     */
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.removePropertyChangeListener(listener);
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

    public Integer getNumber()
    {
        return number;
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }

    public BigDecimal getOperand1()
    {
        return operand1;
    }

    public void setOperand1(BigDecimal operand1)
    {
        this.operand1 = operand1;
        fireResultChange();
    }

    public BigDecimal getOperand2()
    {
        return operand2;
    }

    public void setOperand2(BigDecimal operand2)
    {
        this.operand2 = operand2;
        fireResultChange();
    }

    private void fireResultChange()
    {
        listeners.firePropertyChange("result", null, getResult());
    }
    
    public BigDecimal getResult()
    {
        if (getOperand1() == null || getOperand2() == null) {
            return null;
        }
        
        return getOperand1().add( getOperand2() ); 
    }
}