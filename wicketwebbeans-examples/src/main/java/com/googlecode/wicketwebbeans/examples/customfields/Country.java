package com.googlecode.wicketwebbeans.examples.customfields;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.wicketwebbeans.model.BaseNonJavaEnum;



public class Country extends BaseNonJavaEnum
{
    private static final long serialVersionUID = 3582855853289134520L;

    private volatile static List<Country> cachedEnums;

    public Country(String name, String displayValue)
    {
        super(name, displayValue);
    }

    /**
     * Get the enumerated values.
     *
     * @return the list of values. An empty list is returned if nothing is found.
     */
    public static List<Country> values()
    {
        if (cachedEnums == null) {
            // This is where you would load a list of countries from a database. 
            cachedEnums = new ArrayList<Country>();
            cachedEnums.add( new Country("USA", "United States") );
            cachedEnums.add( new Country("CAN", "Canada") );
            cachedEnums.add( new Country("MEX", "Mexico") );
            cachedEnums.add( new Country("GBR", "Great Britian") );
            cachedEnums.add( new Country("RUS", "Russia") );
        }

        return cachedEnums;
    }

    /**
     * Get the country enum value for the given name.
     *
     * @param enumValue name to match
     *
     * @return a Country, or null if not found.
     */
    public static Country valueOf(String enumValue)
    {
        return (Country)BaseNonJavaEnum.valueOf(enumValue, values());
    }

}
