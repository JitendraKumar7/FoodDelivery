package com.haris.meal4u.ObjectUtil;

public class GeocodeObject {

    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String knownName;


    public String getAddress() {
        return address;
    }

    public GeocodeObject setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public GeocodeObject setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public GeocodeObject setState(String state) {
        this.state = state;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public GeocodeObject setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public GeocodeObject setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getKnownName() {
        return knownName;
    }

    public GeocodeObject setKnownName(String knownName) {
        this.knownName = knownName;
        return this;
    }


}
