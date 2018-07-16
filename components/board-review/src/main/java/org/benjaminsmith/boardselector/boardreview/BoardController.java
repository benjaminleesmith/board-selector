package org.benjaminsmith.boardselector.boardreview;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {
    private BoardClient boardClient;
    private TrustedReviewClient trustedReviewClient;

    public BoardController(BoardClient boardClient, TrustedReviewClient trustedReviewClient) {
        this.boardClient = boardClient;
        this.trustedReviewClient = trustedReviewClient;
    }

    @GetMapping("/boards")
    public ResponseEntity<AllBoards> list() {
        Board[] boards = boardClient.getBoards();

        for(int i = 0; i < boards.length; i++) {
            TrustedReview[] trustedReviews = trustedReviewClient.getTrustedReviewsForBoard(boards[i].getId());
            boards[i].setTrustedReviews(trustedReviews);
        }

        TrustedSite[] trustedSites = trustedReviewClient.getTrustedSites();

        return new ResponseEntity<AllBoards>(new AllBoards(boards, trustedSites), HttpStatus.OK);
    }
}
