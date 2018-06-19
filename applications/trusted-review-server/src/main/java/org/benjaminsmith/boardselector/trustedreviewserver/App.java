package org.benjaminsmith.boardselector.trustedreviewserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.benjaminsmith.boardselector.trustedreview", "org.benjaminsmith.boardselector.trustedsite", "org.benjaminsmith.boardselector.trustedreviewserver"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
