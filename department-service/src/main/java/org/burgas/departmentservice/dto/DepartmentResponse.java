package org.burgas.departmentservice.dto;

import java.time.LocalTime;

public final class DepartmentResponse {

    private Long id;
    private AddressResponse address;
    private LocalTime opensAt;
    private LocalTime closesAt;
    private Boolean opened;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public AddressResponse getAddress() {
        return address;
    }

    @SuppressWarnings("unused")
    public void setAddress(AddressResponse address) {
        this.address = address;
    }

    @SuppressWarnings("unused")
    public LocalTime getOpensAt() {
        return opensAt;
    }

    @SuppressWarnings("unused")
    public void setOpensAt(LocalTime opensAt) {
        this.opensAt = opensAt;
    }

    @SuppressWarnings("unused")
    public LocalTime getClosesAt() {
        return closesAt;
    }

    @SuppressWarnings("unused")
    public void setClosesAt(LocalTime closesAt) {
        this.closesAt = closesAt;
    }

    @SuppressWarnings("unused")
    public Boolean getOpened() {
        return opened;
    }

    @SuppressWarnings("unused")
    public void setOpened(Boolean opened) {
        this.opened = opened;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final DepartmentResponse departmentResponse;

        public Builder() {
            departmentResponse = new DepartmentResponse();
        }

        public Builder id(Long id) {
            this.departmentResponse.id = id;
            return this;
        }

        public Builder address(AddressResponse address) {
            this.departmentResponse.address = address;
            return this;
        }

        public Builder opensAt(LocalTime opensAt) {
            this.departmentResponse.opensAt = opensAt;
            return this;
        }

        public Builder closesAt(LocalTime closesAt) {
            this.departmentResponse.closesAt = closesAt;
            return this;
        }

        public Builder opened(Boolean opened) {
            this.departmentResponse.opened = opened;
            return this;
        }

        public DepartmentResponse build() {
            return departmentResponse;
        }
    }
}
