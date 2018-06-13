package org.benjaminsmith.boardselector.trustedreview;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/board/{boardId}/trusted_reviews")
public class TrustedReviewController {
    private TrustedReviewRepository repository;

    public TrustedReviewController(TrustedReviewRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<TrustedReview>> list(@PathVariable Long boardId) {
        return new ResponseEntity<>(repository.findByBoardId(boardId), HttpStatus.OK);
    }
}
