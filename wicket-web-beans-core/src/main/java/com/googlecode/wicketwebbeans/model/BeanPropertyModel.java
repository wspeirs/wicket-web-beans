/*---
   Copyright 2006-2007 Visual Systems Corporation.
   http://www.vscorp.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
   
        http://www.apache.org/licenses/LICENSE-2.0
   
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
---*/
package com.googlecode.wicketwebbeans.model;


import org.apache.wicket.Component;
import org.apache.wicket.model.IComponentAssignedModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.string.Strings;

import com.googlecode.wicketwebbeans.containers.BeanForm;



/**
 * An extension of PropertyModel so that we can get the backing bean and check for
 * modifications. We only set the bean property if the value from the form has changed with respect
 * to the value that was retrieved from the model when the form was rendered. 
 * This is important because setting one property on a bean via this model may cause other properties to be
 * set indirectly. Wicket dumps the whole form back every time and we do not want to wipe out those
 * properties that were indirectly set.<p>
 * 
 * @param <T>
 * @author Dan Syrstad
 */
public class BeanPropertyModel<T> extends PropertyModel<T> implements IComponentAssignedModel<T>, IWrapModel<T>
{
    private static final long serialVersionUID = 1432855853184541120L;

    private ElementMetaData elementMetaData;
    // This value is tracked from onGetObject to see if the value changes. The assumption is that
    // Wicket calls getObject() when populating the HTML form on the way out, so this is the last value
    // that Wicket got. It is then used for comparison purposes when setObject() is called to see
    // if the value has changed. 
    private Object lastValueGot = null;
    // This flag tracks whether lastValueGot is valid.
    private boolean getObjectCalled = false;
    // If this model is registered with a BeanForm, this is it.
    private BeanForm beanForm = null;

    // TODO Why aren't we using ComponentPropertyModel in 1.3?
    private Component component;
    
    private transient boolean attached = false;
    
    /**
     * Construct a BeanPropertyModel. 
     *
     * @param modelObject
     * @param elementMetaData
     */
    public BeanPropertyModel(Object modelObject, ElementMetaData elementMetaData)
    {
        super(modelObject, elementMetaData.getPropertyName());
        this.elementMetaData = elementMetaData;
    }
    
    public IWrapModel<T> wrapOnAssignment(Component component)
    {
        this.component = component;
        return this;
    }
    
    public IModel getWrappedModel()
    {
        return this;
    }
    
    /**
     * Gets the bean from which the property will be accessed.
     *
     * @return the bean.
     */
    public Object getBean()
    {
        return getTarget();
    }
    
    public ElementMetaData getElementMetaData()
    {
        return elementMetaData;
    }

    /** 
     * {@inheritDoc}
     * @return
     * @see org.apache.wicket.model.AbstractPropertyModel#onGetObject(wicket.Component)
     */
    @Override
    public T getObject()
    {
        attach();
        T value = super.getObject();
        if (!BeanForm.isInSubmit(component)) {
            // Only set these if we're not in submit processing.
            lastValueGot = value;
            getObjectCalled = true;
        }
        
        return value;
    }

    /**
     * {@inheritDoc}
     * Only sets the object if it is different from what getObject() returns. 
     * 
     * @param object 
     * @see org.apache.wicket.model.AbstractPropertyModel#onSetObject(wicket.Component, java.lang.Object)
     */
    @Override
    public void setObject(T object)
    {
        attach();
        Object newValue = object;
        // This, unfortunately, comes in as a String in most cases, so convert it.
        if (newValue instanceof String) {
            final String string = (String)newValue;
            if (!Strings.isEmpty(string)) {
                // and there is a non-null property type for the component
                if (getObjectClass() != null) {
                    // convert the String to the right type
                    IConverter converter = component.getConverter(getObjectClass());
                    if (converter != null) {
                        newValue = converter.convertToObject(string, null);
                    }
                }
            }
        }

        // Below in parens is an equality expression that is inverted to say "not (equal)".
        if ( !(getObjectCalled                        // If lastValueGot is valid.
               && lastValueGot == newValue            // If they're the same object, or both null
               && (lastValueGot == null || lastValueGot.equals(newValue))) ) {
            super.setObject(object);
        }
        
        getObjectCalled = false;
    }

    public BeanForm getBeanForm()
    {
        return beanForm;
    }

    public void setBeanForm(BeanForm beanForm)
    {
        this.beanForm = beanForm;
    }

    private void attach()
    {
        if ( ! attached ) {
            attached = true;
            if (beanForm != null) {
                // Re-register listener when the bean is being re-attached.
                elementMetaData.getBeanMetaData().addPropertyChangeListener(this, beanForm.getListener());
            }
        }
    }

    /** 
     * {@inheritDoc}
     * @see org.apache.wicket.model.AbstractPropertyModel#detach()
     */
    @Override
    public void detach()
    {
        super.detach();
        attached = false;
    }
    

}
