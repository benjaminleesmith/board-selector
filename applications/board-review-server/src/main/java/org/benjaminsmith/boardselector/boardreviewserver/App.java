package org.benjaminsmith.boardselector.boardreviewserver;

import org.benjaminsmith.boardselector.boardreview.BoardClient;
import org.benjaminsmith.boardselector.boardreview.TrustedReviewClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan({"org.benjaminsmith.boardselector.boardreview"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public BoardClient boardClient(
            RestOperations restOperations,
            @Value("${board.server.endpoint}") String boardServerEndpoint
    ) {
        return new BoardClient(restOperations, boardServerEndpoint);
    }

    @Bean
    public TrustedReviewClient trustedReviewClient(RestOperations restOperations, @Value("${trustedreview.server.endpoint}") String trustedServerEndpoint) {
        return new TrustedReviewClient(restOperations, trustedServerEndpoint);
    }

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }
}
