package test.benjaminsmith.boardselector.trustedreviewserver;

import com.jayway.jsonpath.DocumentContext;
import org.benjaminsmith.boardselector.testsupport.TestScenarioSupport;
import org.benjaminsmith.boardselector.trustedreview.TrustedReview;
import org.benjaminsmith.boardselector.trustedreview.TrustedReviewRepository;
import org.benjaminsmith.boardselector.trustedreviewserver.App;
import org.benjaminsmith.boardselector.trustedsite.TrustedSite;
import org.benjaminsmith.boardselector.trustedsite.TrustedSiteRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;

import static com.jayway.jsonpath.JsonPath.parse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TrustedReviewTest {
    private ConfigurableApplicationContext context;
    private TestRestTemplate restTemplate;
    private TrustedReviewRepository trustedReviewRepository;
    private TrustedSiteRepository trustedSiteRepository;

    @Before
    public void setup() {
        context = SpringApplication.run(App.class);
        TestScenarioSupport testScenarioSupport = new TestScenarioSupport("trusted_review_server_test");
        trustedReviewRepository = new TrustedReviewRepository(testScenarioSupport.dataSource);
        trustedSiteRepository = new TrustedSiteRepository(testScenarioSupport.dataSource);

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder().rootUri("http://localhost:8183").basicAuthorization("admin", "password");
        restTemplate = new TestRestTemplate(restTemplateBuilder);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(testScenarioSupport.dataSource);
        jdbcTemplate.execute("DELETE FROM trusted_reviews");
        jdbcTemplate.execute("DELETE FROM trusted_sites");
    }

    @After
    public void tearDown() {
        context.close();
    }

    @Test
    public void testTrustedReviews() {
        TrustedSite createdTrustedSite = trustedSiteRepository.create(new TrustedSite("SUP for the Soul", "http://www.supforthesoul.com/"));

        String listTrustedSitesResponse = restTemplate.getForObject("/trusted_sites", String.class);
        DocumentContext listTrustedSitesJson = parse(listTrustedSitesResponse);
        List<HashMap> trustedSites = listTrustedSitesJson.read("$");
        assertThat(trustedSites.size()).isEqualTo(1);
        HashMap<String, Object> foundTrustedSite = trustedSites.get(0);
        assertThat(new Long((int)foundTrustedSite.get("id"))).isEqualTo(createdTrustedSite.getId());
        assertThat((String)foundTrustedSite.get("name")).isEqualTo("SUP for the Soul");
        assertThat((String)foundTrustedSite.get("url")).isEqualTo("http://www.supforthesoul.com/");

        String createTrustedReviewResponse = restTemplate.postForObject("/admin/trusted_reviews", new TrustedReview(123L, createdTrustedSite.getId(), 90), String.class);
        DocumentContext createdTrustedReviewJson = parse(createTrustedReviewResponse);
        assertThat(new Long((int)createdTrustedReviewJson.read("$.id"))).isNotNull();
        assertThat(new Long((int)createdTrustedReviewJson.read("$.boardId"))).isEqualTo(123L);
        assertThat(new Long((int)createdTrustedReviewJson.read("$.trustedSiteId"))).isEqualTo(createdTrustedSite.getId());
        assertThat((int)createdTrustedReviewJson.read("$.rating")).isEqualTo(90);

        String listTrustedReviewsResponse = restTemplate.getForObject("/board/123/trusted_reviews", String.class);

        DocumentContext trustedReviewsJson = parse(listTrustedReviewsResponse);
        List<HashMap> trustedReviews = trustedReviewsJson.read("$");
        assertThat(trustedReviews.size()).isEqualTo(1);
        HashMap<String, Object> trustedReview = trustedReviews.get(0);
        assertThat((int)trustedReview.get("boardId")).isEqualTo(123);
    }
}
