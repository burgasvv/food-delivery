package org.burgas.employeeservice.dto;

public class IdentityPrincipal {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String authority;
    private Boolean authenticated;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getUsername() {
        return username;
    }

    @SuppressWarnings("unused")
    public String getPassword() {
        return password;
    }

    @SuppressWarnings("unused")
    public String getEmail() {
        return email;
    }

    @SuppressWarnings("unused")
    public String getAuthority() {
        return authority;
    }

    @SuppressWarnings("unused")
    public Boolean getAuthenticated() {
        return authenticated;
    }

    @Override
    public String toString() {
        return "IdentityPrincipal{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", email='" + email + '\'' +
               ", authority='" + authority + '\'' +
               ", authenticated=" + authenticated +
               '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final IdentityPrincipal identityPrincipal;

        public Builder() {
            identityPrincipal = new IdentityPrincipal();
        }

        public Builder id(Long id) {
            this.identityPrincipal.id = id;
            return this;
        }

        public Builder username(String username) {
            this.identityPrincipal.username = username;
            return this;
        }

        public Builder password(String password) {
            this.identityPrincipal.password = password;
            return this;
        }

        public Builder email(String email) {
            this.identityPrincipal.email = email;
            return this;
        }

        public Builder authority(String authority) {
            this.identityPrincipal.authority = authority;
            return this;
        }

        public Builder authenticated(Boolean authenticated) {
            this.identityPrincipal.authenticated = authenticated;
            return this;
        }

        public IdentityPrincipal build() {
            return identityPrincipal;
        }
    }
}
