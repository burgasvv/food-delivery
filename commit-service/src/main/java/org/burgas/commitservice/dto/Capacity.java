package org.burgas.commitservice.dto;

public class Capacity {

    private Long id;
    private Float liters;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public Float getLiters() {
        return liters;
    }

    @SuppressWarnings("unused")
    public void setLiters(Float liters) {
        this.liters = liters;
    }

    public static Builder build() {
        return new Builder();
    }

    public static class Builder {

        private final Capacity capacity;

        public Builder() {
            capacity = new Capacity();
        }

        public Builder id(Long id) {
            this.capacity.id = id;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder liters(Float liters) {
            this.capacity.liters = liters;
            return this;
        }

        public Capacity build() {
            return capacity;
        }
    }
}
