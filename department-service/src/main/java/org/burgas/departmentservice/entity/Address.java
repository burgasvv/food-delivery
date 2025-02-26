package org.burgas.departmentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String city;
    private String street;
    private String house;
    private String apartment;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public String getCity() {
        return city;
    }

    @SuppressWarnings("unused")
    public void setCity(String city) {
        this.city = city;
    }

    @SuppressWarnings("unused")
    public String getStreet() {
        return street;
    }

    @SuppressWarnings("unused")
    public void setStreet(String street) {
        this.street = street;
    }

    @SuppressWarnings("unused")
    public String getHouse() {
        return house;
    }

    @SuppressWarnings("unused")
    public void setHouse(String house) {
        this.house = house;
    }

    @SuppressWarnings("unused")
    public String getApartment() {
        return apartment;
    }

    @SuppressWarnings("unused")
    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Address address;

        public Builder() {
            address = new Address();
        }

        public Builder id(Long id) {
            this.address.id = id;
            return this;
        }

        public Builder city(String city) {
            this.address.city = city;
            return this;
        }

        public Builder street(String street) {
            this.address.street = street;
            return this;
        }

        public Builder house(String house) {
            this.address.house = house;
            return this;
        }

        public Builder apartment(String apartment) {
            this.address.apartment = apartment;
            return this;
        }

        public Address build() {
            return address;
        }
    }
}
