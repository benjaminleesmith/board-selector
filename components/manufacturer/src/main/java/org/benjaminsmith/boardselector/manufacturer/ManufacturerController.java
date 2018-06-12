package org.benjaminsmith.boardselector.manufacturer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/manufacturers")
public class ManufacturerController {
    private ManufacturerRepository repository;

    public ManufacturerController(ManufacturerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Manufacturer>> list() {
        return new ResponseEntity<>(repository.list(), HttpStatus.OK);
    }
}
