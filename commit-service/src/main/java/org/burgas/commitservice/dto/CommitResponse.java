package org.burgas.commitservice.dto;

import java.util.List;

public class CommitResponse {

    private Long id;
    private Long identityId;
    private Token token;
    private Long price;
    private Boolean closed;
    private List<ChooseResponse> chooseResponses;

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
    public Token getToken() {
        return token;
    }

    @SuppressWarnings("unused")
    public void setToken(Token token) {
        this.token = token;
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

    @SuppressWarnings("unused")
    public List<ChooseResponse> getChooseResponses() {
        return chooseResponses;
    }

    @SuppressWarnings("unused")
    public void setChooseResponses(List<ChooseResponse> chooseResponses) {
        this.chooseResponses = chooseResponses;
    }

    @Override
    public String toString() {
        return "CommitResponse{" +
               "id=" + id +
               ", identityId=" + identityId +
               ", token=" + token +
               ", price=" + price +
               ", closed=" + closed +
               ", chooseResponses=" + chooseResponses +
               '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final CommitResponse commitResponse;

        public Builder() {
            commitResponse = new CommitResponse();
        }

        public Builder id(Long id) {
            this.commitResponse.id = id;
            return this;
        }

        public Builder identityId(Long identityId) {
            this.commitResponse.identityId = identityId;
            return this;
        }

        public Builder token(Token token) {
            this.commitResponse.token = token;
            return this;
        }

        public Builder price(Long price) {
            this.commitResponse.price = price;
            return this;
        }

        public Builder closed(Boolean closed) {
            this.commitResponse.closed = closed;
            return this;
        }

        public Builder chooseResponses(List<ChooseResponse> chooseResponses) {
            this.commitResponse.chooseResponses = chooseResponses;
            return this;
        }

        public CommitResponse build() {
            return commitResponse;
        }
    }
}
