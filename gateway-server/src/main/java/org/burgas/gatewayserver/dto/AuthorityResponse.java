package org.burgas.gatewayserver.dto;

import org.springframework.security.core.GrantedAuthority;

public class AuthorityResponse implements GrantedAuthority {

    private Long id;
    private String name;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String getAuthority() {
        return getName();
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
