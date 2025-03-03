package org.burgas.employeeservice.dto;

public class Media {

    private Long id;
    private String name;
    private String contentType;
    private byte[] data;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public byte[] getData() {
        return data;
    }

    @SuppressWarnings("unused")
    public void setData(byte[] data) {
        this.data = data;
    }

    @SuppressWarnings("unused")
    public String getContentType() {
        return contentType;
    }

    @SuppressWarnings("unused")
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Media media;

        public Builder() {
            media = new Media();
        }

        @SuppressWarnings("unused")
        public Builder id(Long id) {
            this.media.id = id;
            return this;
        }

        public Builder name(String name) {
            this.media.name = name;
            return this;
        }

        public Builder data(byte[] data) {
            this.media.data = data;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder contentType(String contentType) {
            this.media.contentType = contentType;
            return this;
        }

        public Media build() {
            return media;
        }
    }
}
