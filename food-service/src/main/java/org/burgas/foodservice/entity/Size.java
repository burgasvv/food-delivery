package org.burgas.foodservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Size {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long size;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public Long getSize() {
        return size;
    }

    @SuppressWarnings("unused")
    public void setSize(Long size) {
        this.size = size;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Size size;

        public Builder() {
            size = new Size();
        }

        public Builder id(Long id) {
            this.size.id = id;
            return this;
        }

        public Builder size(Long size) {
            this.size.size = size;
            return this;
        }

        public Size build() {
            return size;
        }
    }
}
