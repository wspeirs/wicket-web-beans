package com.googlecode.wicketwebbeans.examples.tables;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class InvoiceLine implements Serializable
{
    private static final long serialVersionUID = -6032061214458712439L;


    public enum ItemCode {
        Gears, Sprockets, Chains, Wheels, Gizmos
    };

    private Integer quantity;
    private ItemCode itemCode;
    private BigDecimal cost;
    private int projectCode;

    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public InvoiceLine()
    {
    }

    // JavaBeans compliant method to add a PropertyChangeListener. 
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.addPropertyChangeListener(listener);
    }

    // JavaBeans compliant method to remove a PropertyChangeListener. 
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.removePropertyChangeListener(listener);
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
        fireTotalChange();
    }

    public ItemCode getItemCode()
    {
        return itemCode;
    }

    public void setItemCode(ItemCode itemCode)
    {
        this.itemCode = itemCode;
    }

    public BigDecimal getCost()
    {
        return cost;
    }

    public void setCost(BigDecimal cost)
    {
        // Ensure two decimal places
        this.cost = (cost == null ? null : cost.setScale(2, RoundingMode.HALF_UP));
        fireTotalChange();
    }

    private void fireTotalChange()
    {
        listeners.firePropertyChange("total", null, getTotal());
    }

    public BigDecimal getTotal()
    {
        return cost == null ? null : cost.multiply(BigDecimal.valueOf((long)quantity));
    }

    public int getProjectCode()
    {
        return projectCode;
    }

    public void setProjectCode(int value)
    {
        this.projectCode = value;
    }
}