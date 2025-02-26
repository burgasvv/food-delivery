package org.burgas.commitservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Choose {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long categoryId;
    private String name;
    private String description;
    private Long sizeId;
    private Long capacityId;
    private Long price;
    private Long amount;
    private Boolean isFood;
    private Long commitId;
    private Long mediaId;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public Long getCategoryId() {
        return categoryId;
    }

    @SuppressWarnings("unused")
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    public void setDescription(String description) {
        this.description = description;
    }

    @SuppressWarnings("unused")
    public Long getSizeId() {
        return sizeId;
    }

    @SuppressWarnings("unused")
    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    @SuppressWarnings("unused")
    public Long getCapacityId() {
        return capacityId;
    }

    @SuppressWarnings("unused")
    public void setCapacityId(Long capacityId) {
        this.capacityId = capacityId;
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
    public Long getAmount() {
        return amount;
    }

    @SuppressWarnings("unused")
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @SuppressWarnings("unused")
    public Boolean getFood() {
        return isFood;
    }

    @SuppressWarnings("unused")
    public void setFood(Boolean food) {
        isFood = food;
    }

    @SuppressWarnings("unused")
    public Long getCommitId() {
        return commitId;
    }

    @SuppressWarnings("unused")
    public void setCommitId(Long commitId) {
        this.commitId = commitId;
    }

    @SuppressWarnings("unused")
    public Long getMediaId() {
        return mediaId;
    }

    @SuppressWarnings("unused")
    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Choose choose;

        public Builder() {
            choose = new Choose();
        }

        public Builder id(Long id) {
            this.choose.id = id;
            return this;
        }

        public Builder categoryId(Long categoryId) {
            this.choose.categoryId = categoryId;
            return this;
        }

        public Builder name(String name) {
            this.choose.name = name;
            return this;
        }

        public Builder description(String description) {
            this.choose.description = description;
            return this;
        }

        public Builder sizeId(Long sizeId) {
            this.choose.sizeId = sizeId;
            return this;
        }

        public Builder capacityId(Long capacityId) {
            this.choose.capacityId = capacityId;
            return this;
        }

        public Builder price(Long price) {
            this.choose.price = price;
            return this;
        }

        public Builder amount(Long amount) {
            this.choose.amount = amount;
            return this;
        }

        public Builder isFood(Boolean isFood) {
            this.choose.isFood = isFood;
            return this;
        }

        public Builder commitId(Long commitId) {
            this.choose.commitId = commitId;
            return this;
        }

        public Builder mediaId(Long mediaId) {
            this.choose.mediaId = mediaId;
            return this;
        }

        public Choose build() {
            return choose;
        }
    }
}
