package org.benjaminsmith.boardselector.trustedsite;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trusted_sites")
public class TrustedSiteController {
    private TrustedSiteRepository repository;

    public TrustedSiteController(TrustedSiteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<TrustedSite>> list() {
        return new ResponseEntity<List<TrustedSite>>(repository.list(), HttpStatus.OK);
    }
}
