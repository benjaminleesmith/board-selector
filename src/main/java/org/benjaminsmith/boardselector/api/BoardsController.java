package org.benjaminsmith.boardselector.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BoardsController {
    @GetMapping("/boards")
    public String list() {
        return "hello";
    }
}
