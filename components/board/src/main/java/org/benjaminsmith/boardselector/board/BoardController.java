package org.benjaminsmith.boardselector.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/boards")
public class BoardController {
    private BoardRepository repository;

    public BoardController(BoardRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Board boardToCreate) {
        Board createdBoard = repository.create(boardToCreate);
        return new ResponseEntity(createdBoard, HttpStatus.CREATED);
    }
}
