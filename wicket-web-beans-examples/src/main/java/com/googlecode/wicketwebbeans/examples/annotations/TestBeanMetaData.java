package com.googlecode.wicketwebbeans.examples.annotations;

import static com.googlecode.wicketwebbeans.annotations.Property.EMPTY;

import com.googlecode.wicketwebbeans.annotations.Bean;
import com.googlecode.wicketwebbeans.annotations.Beans;
import com.googlecode.wicketwebbeans.annotations.Property;
import com.googlecode.wicketwebbeans.annotations.Tab;

@Beans({
    @Bean(type = TestBean.class,
        tabs = {
            @Tab(name = "General", propertyNames = { "firstName", "lastName", "idNumber" }),
            @Tab(name = "Address", propertyNames = { 
                "address1", EMPTY, EMPTY, 
                "address2", EMPTY, EMPTY, "city", "state", "zip" })
        },
        
        // Customize certain properties from above.
        properties = {
          @Property(name = "firstName", required = true, maxLength = 10),
          @Property(name = "lastName", required = true)
        }
    ),

    // Inherits from default context.
    @Bean(type = TestBean.class, context = "someContext", propertyNames = "-idNumber")
})
public interface TestBeanMetaData
{
    // This is just an interface to hold the annotations.
}
