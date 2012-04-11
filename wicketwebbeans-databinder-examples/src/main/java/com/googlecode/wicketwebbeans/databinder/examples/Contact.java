package com.googlecode.wicketwebbeans.databinder.examples;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Contact implements Serializable
{
    private static final long serialVersionUID = 2042855853286535579L;

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Category category;

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    @Column(nullable=false, unique=false)
    public String getFirstName()
    {
        return firstName;
    }
    
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    @Column(nullable=false, unique=false)
    public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber()
    {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
    
    @ManyToOne
    public Category getCategory()
    {
        return category;
    }
    
    public void setCategory(Category category)
    {
        this.category = category;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		return result;
	}

    @Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
                }
		if (obj == null) {
			return false;
                }
		if (!(obj instanceof Contact)) {
			return false;
                }
		final Contact other = (Contact) obj;
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
                        }
		} else {
                    if (!firstName.equals(other.firstName)) {
                        return false;
                    }
                }
		if (id == null) {
			if (other.id != null) {
				return false;
                        }
		} else {
                    if (!id.equals(other.id)) {
                        return false;
                    }
                }
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
                        }
		} else {
                    if (!lastName.equals(other.lastName)) {
                        return false;
                    }
                }
		if (phoneNumber == null) {
			if (other.phoneNumber != null) {
				return false;
                        }
		} else {
                    if (!phoneNumber.equals(other.phoneNumber)) {
                        return false;
                    }
                }
		return true;
	}
}