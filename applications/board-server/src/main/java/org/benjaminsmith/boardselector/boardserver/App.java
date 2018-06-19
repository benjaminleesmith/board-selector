package org.benjaminsmith.boardselector.boardserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.benjaminsmith.boardselector.construction", "org.benjaminsmith.boardselector.manufacturer", "org.benjaminsmith.boardselector.board", "org.benjaminsmith.boardselector.boardserver"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
