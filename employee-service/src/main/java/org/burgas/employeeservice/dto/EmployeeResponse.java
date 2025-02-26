package org.burgas.employeeservice.dto;

import org.burgas.departmentservice.dto.DepartmentResponse;
import org.burgas.identityservice.dto.IdentityResponse;

public class EmployeeResponse {

    private Long id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private Long mediaId;
    private DepartmentResponse department;
    private IdentityResponse identity;

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
    public Long getMediaId() {
        return mediaId;
    }

    @SuppressWarnings("unused")
    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    @SuppressWarnings("unused")
    public DepartmentResponse getDepartment() {
        return department;
    }

    @SuppressWarnings("unused")
    public void setDepartment(DepartmentResponse department) {
        this.department = department;
    }

    @SuppressWarnings("unused")
    public IdentityResponse getIdentity() {
        return identity;
    }

    @SuppressWarnings("unused")
    public void setIdentity(IdentityResponse identity) {
        this.identity = identity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final EmployeeResponse employeeResponse;

        public Builder() {
            employeeResponse = new EmployeeResponse();
        }

        public Builder id(Long id) {
            this.employeeResponse.id = id;
            return this;
        }

        public Builder firstname(String firstname) {
            this.employeeResponse.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.employeeResponse.lastname = lastname;
            return this;
        }

        public Builder patronymic(String patronymic) {
            this.employeeResponse.patronymic = patronymic;
            return this;
        }

        public Builder mediaId(Long mediaId) {
            this.employeeResponse.mediaId = mediaId;
            return this;
        }

        public Builder department(DepartmentResponse department) {
            this.employeeResponse.department = department;
            return this;
        }

        public Builder identity(IdentityResponse identity) {
            this.employeeResponse.identity = identity;
            return this;
        }

        public EmployeeResponse build() {
            return employeeResponse;
        }
    }
}
