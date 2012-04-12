package com.googlecode.wicketwebbeans.examples.dependentfields;

public enum Model {
    Crown(Make.Ford, "Crown"),
    Escape(Make.Ford, "Escape"),
    Expedition(Make.Ford, "Expedition"),
    Explorer(Make.Ford, "Explorer"),
    F150(Make.Ford, "F-150"),
    A4(Make.Audi, "A4"),
    A6(Make.Audi, "A6"),
    TT(Make.Audi, "TT"),
    Prelude(Make.Honda, "Prelude"),
    Civic(Make.Honda, "Civic"),
    S2000(Make.Honda, "S2000");
    
    private Make make;
    private String modelName;
    
    private Model(Make make, String modelName)
    {
        this.make = make;
        this.modelName = modelName;
    }

    public Make getMake()
    {
        return make;
    }

    public String getModelName()
    {
        return modelName;
    }
    
    public String toString()
    {
        return make.toString() + " " + modelName;
    }
}
