package com.googlecode.wicketwebbeans.databinder.examples;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category implements Serializable
{
    private static final long serialVersionUID = 2342855853286535421L;

    private Long id;
    private String name;

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    @Column(nullable=false, unique=true)
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Category)) {
            return false;
        }
        final Category other = (Category) obj;
        if (this.id == null)
        {
            if (other.id != null) {
                return false;
            }
        }
        else {
            if (!this.id.equals(other.id)) {
                return false;
            }
        }
        if (this.name == null)
        {
            if (other.name != null) {
                return false;
            }
        }
        else {
            if (!this.name.equals(other.name)) {
                return false;
            }
        }
        return true;
    }
}