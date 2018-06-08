package org.benjaminsmith.boardselector;

import org.benjaminsmith.boardselector.construction.ConstructionRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class BoardSelectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoardSelectorApplication.class, args);
    }

    @Bean
    ConstructionRepository constructionRepository(DataSource dataSource) {
        return new ConstructionRepository(dataSource);
    }
}
