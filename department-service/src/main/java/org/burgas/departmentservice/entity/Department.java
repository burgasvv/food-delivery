package org.burgas.departmentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long addressId;
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
    public Long getAddressId() {
        return addressId;
    }

    @SuppressWarnings("unused")
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
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

        private final Department department;

        public Builder() {
            department = new Department();
        }

        public Builder id(Long id) {
            this.department.id = id;
            return this;
        }

        public Builder addressId(Long addressId) {
            this.department.addressId = addressId;
            return this;
        }

        public Builder opensAt(LocalTime opensAt) {
            this.department.opensAt = opensAt;
            return this;
        }

        public Builder closesAt(LocalTime closesAt) {
            this.department.closesAt = closesAt;
            return this;
        }

        public Builder opened(Boolean opened) {
            this.department.opened = opened;
            return this;
        }

        public Department build() {
            return department;
        }
    }
}
