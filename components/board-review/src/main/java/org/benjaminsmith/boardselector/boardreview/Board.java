package org.benjaminsmith.boardselector.boardreview;

public class Board {
    private long id;
    private String model;
    private long constructionId;
    private long manufacturerId;
    private TrustedReview[] trustedReviews;

    public void setTrustedReviews(TrustedReview[] trustedReviews) {
        this.trustedReviews = trustedReviews;
    }

    public long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public long getConstructionId() {
        return constructionId;
    }

    public long getManufacturerId() {
        return manufacturerId;
    }

    public TrustedReview[] getTrustedReviews() {
        return this.trustedReviews;
    }
}
