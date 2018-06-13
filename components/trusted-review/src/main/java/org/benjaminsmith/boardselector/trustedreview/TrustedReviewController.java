package org.benjaminsmith.boardselector.trustedreview;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TrustedReviewController {
    private TrustedReviewRepository repository;

    public TrustedReviewController(TrustedReviewRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/board/{boardId}/trusted_reviews")
    public ResponseEntity<List<TrustedReview>> list(@PathVariable Long boardId) {
        return new ResponseEntity<>(repository.findByBoardId(boardId), HttpStatus.OK);
    }

    @PostMapping("/admin/trusted_reviews")
    public ResponseEntity<TrustedReview> create(@RequestBody TrustedReview trustedReviewToCreate) {
        TrustedReview createdTrustedReview = repository.create(trustedReviewToCreate);
        return new ResponseEntity<TrustedReview>(createdTrustedReview, HttpStatus.CREATED);
    }
}
