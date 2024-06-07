package com.phuclong.milktea.milktea.design.builder;

public abstract class AddressBuilder {
    abstract public void buildStreet(String street);
    abstract public void buildCity(String city);
    abstract public void buildProvide(String provide);
    abstract public void buildCountry(String country);
}
