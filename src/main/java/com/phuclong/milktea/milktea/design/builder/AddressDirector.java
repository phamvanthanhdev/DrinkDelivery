package com.phuclong.milktea.milktea.design.builder;

public class AddressDirector {
    public void construct(AddressBuilder addressBuilder, String street, String city, String provide, String country){
        addressBuilder.buildStreet(street);
        addressBuilder.buildCity(city);
        addressBuilder.buildProvide(provide);
        addressBuilder.buildCountry(country);
    }
}
