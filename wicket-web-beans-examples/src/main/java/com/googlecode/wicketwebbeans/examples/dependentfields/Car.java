package com.googlecode.wicketwebbeans.examples.dependentfields;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Car implements Serializable
{
    private static final long serialVersionUID = 1442855853283191120L;

    private Make make;
    private Model model;
    
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public Car()
    {
    }

    /**
     * JavaBeans compliant method to add a PropertyChangeListener. 
     */
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.addPropertyChangeListener(listener);
    }
    
    /**
     * JavaBeans compliant method to remove a PropertyChangeListener. 
     */
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.removePropertyChangeListener(listener);
    }

    public Make getMake()
    {
        return make;
    }

    public void setMake(Make make)
    {
        Make old = this.make;
        this.make = make;
        
        if (old != this.make) {
            Model oldModel = model;
            model = null; // Clear current model - not valid for make.
            listeners.firePropertyChange("model", oldModel, model);
        }
    }

    public Model getModel()
    {
        return model;
    }

    public void setModel(Model model)
    {
        this.model = model;
    }
}
