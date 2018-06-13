package org.benjaminsmith.boardselector.trustedreview;

import org.benjaminsmith.boardselector.trustedsite.TrustedSite;

public class TrustedReview {
    private long boardId;
    private long trustedSiteId;
    private int rating;
    private long id;
    private TrustedSite trustedSite;

    public TrustedReview() {}

    public TrustedReview(long id, long boardId, long trustedSiteId, int rating, String trustedSiteName) {
        this.id = id;
        this.boardId = boardId;
        this.trustedSiteId = trustedSiteId;
        this.rating = rating;
        this.trustedSite = new TrustedSite(trustedSiteId, trustedSiteName);
    }

    public TrustedReview(long boardId, long trustedSiteId, int rating) {
        this.boardId = boardId;
        this.trustedSiteId = trustedSiteId;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public long getBoardId() {
        return boardId;
    }

    public long getTrustedSiteId() {
        return trustedSiteId;
    }

    public int getRating() {
        return rating;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TrustedSite getTrustedSite() {
        return trustedSite;
    }
}
