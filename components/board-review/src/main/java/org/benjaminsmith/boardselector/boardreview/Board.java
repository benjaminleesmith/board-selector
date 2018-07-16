package org.benjaminsmith.boardselector.boardreview;

public class Board {
    private long id;
    private String model;
    private long constructionId;
    private long manufacturerId;
    private TrustedReview[] trustedReviews;

    private Board() {}

    public Board(long id, String model, long constructionId, long manufacturerId) {
        this.id = id;
        this.model = model;
        this.constructionId = constructionId;
        this.manufacturerId = manufacturerId;
    }

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
