package org.burgas.databaseserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String value;

    public Long getId() {
        return id;
    }

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

    @SuppressWarnings("unused")
    public String getValue() {
        return value;
    }

    @SuppressWarnings("unused")
    public void setValue(String value) {
        this.value = value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Token token;

        public Builder() {
            token = new Token();
        }

        public Builder id(Long id) {
            this.token.id = id;
            return this;
        }

        public Builder name(String name) {
            this.token.name = name;
            return this;
        }

        public Builder value(String value) {
            this.token.value = value;
            return this;
        }

        public Token build() {
            return token;
        }
    }
}
