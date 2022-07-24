package edu.miu.cs544.week05.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String number;
    private String street;
    private String city;
    private String state;
    private String zip;

    public Address() {
    }

    public Address(String number, String street, String city, String state, String zip) {
        this.number = number;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public Address(Address address) {
        this(address.number, address.street, address.city, address.state, address.zip);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "Address{" +
                "number='" + number + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
