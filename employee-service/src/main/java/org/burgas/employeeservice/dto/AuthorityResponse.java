package org.burgas.employeeservice.dto;

public class AuthorityResponse {

    private Long id;
    private String name;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final AuthorityResponse authorityResponse;

        public Builder() {
            authorityResponse = new AuthorityResponse();
        }

        public Builder id(Long id) {
            this.authorityResponse.id = id;
            return this;
        }

        public Builder name(String name) {
            this.authorityResponse.name = name;
            return this;
        }

        public AuthorityResponse build() {
            return authorityResponse;
        }
    }
}
