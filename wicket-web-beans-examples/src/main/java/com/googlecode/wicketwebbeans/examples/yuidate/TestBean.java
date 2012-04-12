package com.googlecode.wicketwebbeans.examples.yuidate;

import java.io.Serializable;
import java.util.GregorianCalendar;


public class TestBean implements Serializable 
{
    private static final long serialVersionUID = 1218462013665931174L;

    private java.util.Date javaUtilDate;
    private java.sql.Date javaSqlDate;
    private java.sql.Time javaSqlTime;
    private java.sql.Timestamp javaSqlTimestamp;
//    private java.util.GregorianCalendar javaUtilCalendar = new GregorianCalendar();
    private java.util.Date beanpropsCustomized;

    public TestBean()
    {
    }

    public java.util.Date getJavaUtilDate()
    {
        return javaUtilDate;
    }

    public void setJavaUtilDate(java.util.Date javaUtilDate)
    {
        this.javaUtilDate = javaUtilDate;
    }

    public java.sql.Date getJavaSqlDate()
    {
        return javaSqlDate;
    }

    public void setJavaSqlDate(java.sql.Date javaSqlDate)
    {
        this.javaSqlDate = javaSqlDate;
    }

    public java.sql.Time getJavaSqlTime()
    {
        return javaSqlTime;
    }

    public void setJavaSqlTime(java.sql.Time javaSqlTime)
    {
        this.javaSqlTime = javaSqlTime;
    }

    public java.sql.Timestamp getJavaSqlTimestamp()
    {
        return javaSqlTimestamp;
    }

    public void setJavaSqlTimestamp(java.sql.Timestamp javaSqlTimestamp)
    {
        this.javaSqlTimestamp = javaSqlTimestamp;
    }

//    public java.util.GregorianCalendar getJavaUtilCalendar()
//    {
//        return javaUtilCalendar;
//    }
//
//    public void setJavaUtilCalendar(java.util.GregorianCalendar javaUtilCalendar)
//    {
//        this.javaUtilCalendar = javaUtilCalendar;
//    }

    public java.util.Date getBeanpropsCustomized()
    {
        return beanpropsCustomized;
    }

    public void setBeanpropsCustomized(java.util.Date beanpropsCustomized)
    {
        this.beanpropsCustomized = beanpropsCustomized;
    }

}