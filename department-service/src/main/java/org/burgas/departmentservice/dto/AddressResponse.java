package org.burgas.departmentservice.dto;

public class AddressResponse {

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

        private final AddressResponse addressResponse
                ;

        public Builder() {
            addressResponse = new AddressResponse();
        }

        public Builder id(Long id) {
            this.addressResponse.id = id;
            return this;
        }

        public Builder city(String city) {
            this.addressResponse.city = city;
            return this;
        }

        public Builder street(String street) {
            this.addressResponse.street = street;
            return this;
        }

        public Builder house(String house) {
            this.addressResponse.house = house;
            return this;
        }

        public Builder apartment(String apartment) {
            this.addressResponse.apartment = apartment;
            return this;
        }

        public AddressResponse build() {
            return addressResponse;
        }
    }
}
