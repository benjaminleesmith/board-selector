package org.benjaminsmith.boardselector.construction;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/constructions")
public class ConstructionController {
    private ConstructionRepository repository;

    public ConstructionController(ConstructionRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Construction constructionToCreate) {
        Construction createdConstruction = repository.create(constructionToCreate);
        return new ResponseEntity<>(createdConstruction, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Construction>> list() {
        return new ResponseEntity<>(repository.list(), HttpStatus.OK);
    }
}
