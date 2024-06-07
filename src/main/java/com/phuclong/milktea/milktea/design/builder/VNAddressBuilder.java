package com.phuclong.milktea.milktea.design.builder;

import com.phuclong.milktea.milktea.model.Address;

public class VNAddressBuilder extends AddressBuilder{
    private Address address = new Address();

    public Address getAddress() {
        return address;
    }

    @Override
    public void buildStreet(String street) {
        address.setStreetAddress(street);
    }

    @Override
    public void buildCity(String city) {
        address.setCity(city);
    }

    @Override
    public void buildProvide(String provide) {
        address.setStateProvice(provide);
    }

    @Override
    public void buildCountry(String country) {
        address.setCounty(country);
    }
}
