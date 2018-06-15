package org.benjaminsmith.boardselector.boardreview;

import org.springframework.web.client.RestOperations;

public class TrustedReviewClient {
    private final RestOperations restOperations;
    private final String endpoint;

    public TrustedReviewClient(RestOperations restOperations, String endpoint) {
        this.restOperations = restOperations;
        this.endpoint = endpoint;
    }

    public TrustedReview[] getTrustedReviewsForBoard(long boardId) {
        String url = endpoint + "/board/" + boardId + "/trusted_reviews";
        return restOperations.getForObject(url, TrustedReview[].class);
    }
}
