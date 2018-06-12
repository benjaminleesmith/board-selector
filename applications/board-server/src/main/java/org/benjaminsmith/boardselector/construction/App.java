package org.benjaminsmith.boardselector.construction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.benjaminsmith.boardselector.construction", "org.benjaminsmith.boardselector.manufacturer", "org.benjaminsmith.boardselector.board"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
