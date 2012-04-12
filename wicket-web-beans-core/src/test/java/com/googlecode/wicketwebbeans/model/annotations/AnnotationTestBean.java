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

package com.googlecode.wicketwebbeans.model.annotations;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnnotationTestBean implements Serializable
{
    private static final long serialVersionUID = -8500883418534059147L;

    public enum ColorEnum { 
        RedishBlack ("Redish Black"), 
        Green ("Green"), 
        Blue ("Blue"), 
        Black ("Black"), 
        Yellow ("Yellow"), 
        Purple ("Magenta"), 
        Orange ("Orange");
        
        private String readable;
        
        ColorEnum(String readable)
        {
            this.readable = readable;
        }
        
        public String toString()
        {
            return readable;
        }
    };
    
    private String firstName;
    private String lastName;
    private Date startDate;
    private java.sql.Date dateOnly;
    private java.sql.Time timeOnly;
    private java.sql.Timestamp dateTimestamp;
    private Integer age;
    private BigDecimal savingsAmount;
    private String gender;
    private Boolean isActive;
    private boolean isActivePrimitive;
    private ColorEnum color;
    private BigDecimal operand1;
    private BigDecimal operand2;
    private AnnotationTestBean2 testBean2;
    private AnnotationTestBean2 blockBean;
    private AnnotationTestBean2 inlineBean;
    private AnnotationTestBean2 popupBean;
    private List<AnnotationTestBean2> beans;
    private List<ColorEnum> palette = new ArrayList<ColorEnum>();
    private String description;
    private SubComponent subComponent;
    
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public AnnotationTestBean()
    {
    }

    /**
     * JavaBeans compliant method to add a PropertyChangeListener. 
     *
     * @param listener the listener.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.addPropertyChangeListener(listener);
    }
    
    /**
     * JavaBeans compliant method to remove a PropertyChangeListener. 
     *
     * @param listener the listener.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.removePropertyChangeListener(listener);
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
        this.firstName = (firstName == null ? null : firstName.toUpperCase());
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
     * @return a String.
     */
    public String getGender()
    {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param gender a String.
     */
    public void setGender(String gender)
    {
        this.gender = gender;
    }

    
    public java.sql.Date getDateOnly()
    {
        return dateOnly;
    }

    
    public void setDateOnly(java.sql.Date dateOnly)
    {
        this.dateOnly = dateOnly;
    }

    
    public java.sql.Timestamp getDateTimestamp()
    {
        return dateTimestamp;
    }

    
    public void setDateTimestamp(java.sql.Timestamp dateTimestamp)
    {
        this.dateTimestamp = dateTimestamp;
    }

    
    public java.sql.Time getTimeOnly()
    {
        return timeOnly;
    }

    
    public void setTimeOnly(java.sql.Time timeOnly)
    {
        this.timeOnly = timeOnly;
    }

    
    public Boolean getIsActive()
    {
        return isActive;
    }

    
    public void setIsActive(Boolean isActive)
    {
        this.isActive = isActive;
    }

    
    public boolean isActivePrimitive()
    {
        return isActivePrimitive;
    }

    
    public void setActivePrimitive(boolean isActivePrimitive)
    {
        this.isActivePrimitive = isActivePrimitive;
    }

    
    public ColorEnum getColor()
    {
        return color;
    }

    
    public void setColor(ColorEnum color)
    {
        this.color = color;
    }

    /**
     * Gets the operand1.
     *
     * @return a BigDecimal.
     */
    public BigDecimal getOperand1()
    {
        return operand1;
    }

    /**
     * Sets operand1.
     *
     * @param operand1 a BigDecimal.
     */
    public void setOperand1(BigDecimal operand1)
    {
        this.operand1 = operand1;
        fireResultChange();
    }

    /**
     * Gets the operand2.
     *
     * @return a BigDecimal.
     */
    public BigDecimal getOperand2()
    {
        return operand2;
    }

    /**
     * Sets operand2.
     *
     * @param operand2 a BigDecimal.
     */
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

    /**
     * Gets the beans.
     *
     * @return a List<AnnotationTestBean2>.
     */
    public List<AnnotationTestBean2> getBeans()
    {
        return beans;
    }

    /**
     * Sets beans.
     *
     * @param beans a List<AnnotationTestBean2>.
     */
    public void setBeans(List<AnnotationTestBean2> beans)
    {
        this.beans = beans;
    }

    /**
     * Gets the testBean2.
     *
     * @return a AnnotationTestBean2.
     */
    public AnnotationTestBean2 getTestBean2()
    {
        return testBean2;
    }

    /**
     * Sets testBean2.
     *
     * @param testBean2 a AnnotationTestBean2.
     */
    public void setTestBean2(AnnotationTestBean2 testBean2)
    {
        this.testBean2 = testBean2;
    }

    
    /**
     * Gets the palette.
     *
     * @return the palette.
     */
    public List<ColorEnum> getPalette()
    {
        return palette;
    }

    
    /**
     * Sets the palette.
     *
     * @param palette the palette to set.
     */
    public void setPalette(List<ColorEnum> palette)
    {
        this.palette = palette;
    }

    /**
     * Gets the palette.
     *
     * @return the palette.
     */
    public List<ColorEnum> getPalette2()
    {
        return palette;
    }

    
    /**
     * Gets the blockBean.
     *
     * @return the blockBean.
     */
    public AnnotationTestBean2 getBlockBean()
    {
        return blockBean;
    }

    
    /**
     * Sets the blockBean.
     *
     * @param blockBean the blockBean to set.
     */
    public void setBlockBean(AnnotationTestBean2 blockBean)
    {
        this.blockBean = blockBean;
    }

    
    /**
     * Gets the inlineBean.
     *
     * @return the inlineBean.
     */
    public AnnotationTestBean2 getInlineBean()
    {
        return inlineBean;
    }

    
    /**
     * Sets the inlineBean.
     *
     * @param inlineBean the inlineBean to set.
     */
    public void setInlineBean(AnnotationTestBean2 inlineBean)
    {
        this.inlineBean = inlineBean;
    }

    
    /**
     * Gets the popupBean.
     *
     * @return the popupBean.
     */
    public AnnotationTestBean2 getPopupBean()
    {
        return popupBean;
    }

    
    /**
     * Sets the popupBean.
     *
     * @param popupBean the popupBean to set.
     */
    public void setPopupBean(AnnotationTestBean2 popupBean)
    {
        this.popupBean = popupBean;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * Gets the subComponent.
     *
     * @return the subComponent.
     */
    public SubComponent getSubComponent()
    {
        return subComponent;
    }
    
    /**
     * Sets the subComponent.
     *
     * @param subComponent the subComponent to set.
     */
    public void setSubComponent(SubComponent subComponent)
    {
        this.subComponent = subComponent;
    }

    public static final class SubComponent implements Serializable
    {
        private static final long serialVersionUID = 1214462067665151174L;

        private Date date;
        private String field;
        
        /**
         * Gets the date.
         *
         * @return the date.
         */
        public Date getDate()
        {
            return date;
        }
        
        /**
         * Sets the date.
         *
         * @param date the date to set.
         */
        public void setDate(Date date)
        {
            this.date = date;
        }
        
        /**
         * Gets the field.
         *
         * @return the field.
         */
        public String getField()
        {
            return field;
        }
        
        /**
         * Sets the field.
         *
         * @param field the field to set.
         */
        public void setField(String field)
        {
            this.field = field;
        }
        
    }

}
