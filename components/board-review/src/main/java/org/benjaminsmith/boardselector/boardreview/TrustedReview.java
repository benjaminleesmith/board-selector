package org.benjaminsmith.boardselector.boardreview;

public class TrustedReview {
    private long id;
    private long trustedSiteId;
    private int rating;

    private TrustedReview() {}

    public TrustedReview(long id, long trustedSiteId, int rating) {
        this.id = id;
        this.trustedSiteId = trustedSiteId;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public long getTrustedSiteId() {
        return trustedSiteId;
    }

    public int getRating() {
        return rating;
    }
}
