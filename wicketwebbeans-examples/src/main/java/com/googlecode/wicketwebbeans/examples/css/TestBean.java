package com.googlecode.wicketwebbeans.examples.css;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestBean implements Serializable 
{
    private static final long serialVersionUID = 4911652973164473144L;

    private String firstName;
    private String lastName;
    private List<RowBean> rows;
    
    public TestBean()
    {
        rows = new ArrayList<RowBean>();
        rows.add( new RowBean("Row 1", 10) );
        rows.add( new RowBean("Row 2", 20) );
        rows.add( new RowBean("Row 3", -10) );
        rows.add( new RowBean("Row 4", -20) );
        rows.add( new RowBean("Row 5", 0) );
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

    public List<RowBean> getRows()
    {
        return rows;
    }

    public void setRows(List<RowBean> rows)
    {
        this.rows = rows;
    }


}