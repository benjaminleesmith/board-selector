package org.benjaminsmith.boardselector.boardreview;

public class TrustedReview {
    private long id;
    private long trustedSiteId;
    private int rating;
    private TrustedSite trustedSite;

    private TrustedReview() {}

    public long getId() {
        return id;
    }

    public long getTrustedSiteId() {
        return trustedSiteId;
    }

    public int getRating() {
        return rating;
    }

    public TrustedSite getTrustedSite() {
        return trustedSite;
    }
}
