package org.burgas.employeeservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private Long departmentId;
    private Long identityId;
    private Long mediaId;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public String getFirstname() {
        return firstname;
    }

    @SuppressWarnings("unused")
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @SuppressWarnings("unused")
    public String getLastname() {
        return lastname;
    }

    @SuppressWarnings("unused")
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @SuppressWarnings("unused")
    public String getPatronymic() {
        return patronymic;
    }

    @SuppressWarnings("unused")
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @SuppressWarnings("unused")
    public Long getDepartmentId() {
        return departmentId;
    }

    @SuppressWarnings("unused")
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @SuppressWarnings("unused")
    public Long getIdentityId() {
        return identityId;
    }

    @SuppressWarnings("unused")
    public void setIdentityId(Long identityId) {
        this.identityId = identityId;
    }

    @SuppressWarnings("unused")
    public Long getMediaId() {
        return mediaId;
    }

    @SuppressWarnings("unused")
    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Employee employee;

        public Builder() {
            employee = new Employee();
        }

        public Builder id(Long id) {
            this.employee.id = id;
            return this;
        }

        public Builder firstname(String firstname) {
            this.employee.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.employee.lastname = lastname;
            return this;
        }

        public Builder patronymic(String patronymic) {
            this.employee.patronymic = patronymic;
            return this;
        }

        public Builder departmentId(Long departmentId) {
            this.employee.departmentId = departmentId;
            return this;
        }

        public Builder identityId(Long identityId) {
            this.employee.identityId = identityId;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder mediaId(Long mediaId) {
            this.employee.mediaId = mediaId;
            return this;
        }

        public Employee build() {
            return employee;
        }
    }
}
