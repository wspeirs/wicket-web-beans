package com.googlecode.wicketwebbeans.examples.login;

import java.io.Serializable;

import com.googlecode.wicketwebbeans.annotations.Property;
import com.googlecode.wicketwebbeans.fields.PasswordField;


public class LoginBean implements Serializable 
{
    private static final long serialVersionUID = 4911652973164473144L;

    private String userName;
    private String password;
    
    public LoginBean()
    {
    }

    /**
     * Gets the userName.
     *
     * @return a String.
     */
    @Property(colspan = 3)
    public String getUserName()
    {
        return userName;
    }

    /**
     * Sets userName.
     *
     * @param userName a String.
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * Gets the password.
     *
     * @return a String.
     */
    @Property(fieldType = PasswordField.class, colspan = 3)
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password a String.
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
}