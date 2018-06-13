package test.benjaminsmith.boardselector.trustedreviewserver;

import com.jayway.jsonpath.DocumentContext;
import org.benjaminsmith.boardselector.testsupport.TestScenarioSupport;
import org.benjaminsmith.boardselector.trustedreview.TrustedReview;
import org.benjaminsmith.boardselector.trustedreview.TrustedReviewRepository;
import org.benjaminsmith.boardselector.trustedreviewserver.App;
import org.benjaminsmith.boardselector.trustedsite.TrustedSite;
import org.benjaminsmith.boardselector.trustedsite.TrustedSiteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

import static com.jayway.jsonpath.JsonPath.parse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = RANDOM_PORT)
public class TrustedReviewTest {
    @LocalServerPort
    private String port;

    private TestRestTemplate restTemplate;
    private TrustedReviewRepository trustedReviewRepository;
    private TrustedSiteRepository trustedSiteRepository;

    @Before
    public void setup() {
        TestScenarioSupport testScenarioSupport = new TestScenarioSupport("trusted_review_server_test");
        trustedReviewRepository = new TrustedReviewRepository(testScenarioSupport.dataSource);
        trustedSiteRepository = new TrustedSiteRepository(testScenarioSupport.dataSource);

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder().rootUri("http://localhost:"+port);
        restTemplate = new TestRestTemplate(restTemplateBuilder);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(testScenarioSupport.dataSource);
        jdbcTemplate.execute("DELETE FROM trusted_reviews");
        jdbcTemplate.execute("DELETE FROM trusted_sites");
    }

    @Test
    public void testTrustedReviews() {
        TrustedSite trustedSite = trustedSiteRepository.create(new TrustedSite("SUP for the Soul"));
        trustedReviewRepository.create(new TrustedReview(123, trustedSite.getId(), 90));

        String response = restTemplate.getForObject("/board/123/trusted_reviews", String.class);

        DocumentContext trustedReviewsJson = parse(response);
        List<HashMap> trustedReviews = trustedReviewsJson.read("$");
        assertThat(trustedReviews.size()).isEqualTo(1);
        HashMap<String, Object> trustedReview = trustedReviews.get(0);
        assertThat((int)trustedReview.get("boardId")).isEqualTo(123);
    }
}