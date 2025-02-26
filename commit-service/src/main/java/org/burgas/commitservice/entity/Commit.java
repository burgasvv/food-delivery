package org.burgas.commitservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Commit {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long identityId;
    private Long tokenId;
    private Long price;
    private Boolean closed;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
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
    public Long getTokenId() {
        return tokenId;
    }

    @SuppressWarnings("unused")
    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    @SuppressWarnings("unused")
    public Long getPrice() {
        return price;
    }

    @SuppressWarnings("unused")
    public void setPrice(Long price) {
        this.price = price;
    }

    @SuppressWarnings("unused")
    public Boolean getClosed() {
        return closed;
    }

    @SuppressWarnings("unused")
    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    @Override
    public String toString() {
        return "Commit{" +
               "id=" + id +
               ", identityId=" + identityId +
               ", tokenId=" + tokenId +
               ", price=" + price +
               ", closed=" + closed +
               '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Commit commit;

        public Builder() {
            commit = new Commit();
        }

        public Builder id(Long id) {
            this.commit.id = id;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder identityId(Long identityId) {
            this.commit.identityId = identityId;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder tokenId(Long tokenId) {
            this.commit.tokenId = tokenId;
            return this;
        }

        public Builder price(Long price) {
            this.commit.price = price;
            return this;
        }

        public Builder closed(Boolean closed) {
            this.commit.closed = closed;
            return this;
        }

        public Commit build() {
            return commit;
        }
    }
}
