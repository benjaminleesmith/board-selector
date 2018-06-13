package org.benjaminsmith.boardselector.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/boards")
public class BoardController {
    private BoardRepository repository;

    public BoardController(BoardRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Board> create(@RequestBody Board boardToCreate) {
        Board createdBoard = repository.create(boardToCreate);
        return new ResponseEntity(createdBoard, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Board>> list() {
        return new ResponseEntity(repository.list(), HttpStatus.OK);
    }
}
