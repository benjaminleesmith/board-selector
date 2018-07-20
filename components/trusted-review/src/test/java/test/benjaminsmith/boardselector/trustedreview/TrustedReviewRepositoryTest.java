package test.benjaminsmith.boardselector.trustedreview;

import org.benjaminsmith.boardselector.testsupport.TestScenarioSupport;
import org.benjaminsmith.boardselector.trustedreview.*;
import org.benjaminsmith.boardselector.trustedsite.TrustedSite;
import org.benjaminsmith.boardselector.trustedsite.TrustedSiteRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TrustedReviewRepositoryTest {
    private TrustedReviewRepository repository;
    private TestScenarioSupport testScenarioSupport;
    private JdbcTemplate jdbcTemplate;
    private TrustedSiteRepository trustedSiteRepository;

    @Before
    public void setup() {
        testScenarioSupport = new TestScenarioSupport("trusted_review_server_test");
        repository = new TrustedReviewRepository(testScenarioSupport.dataSource);
        trustedSiteRepository = new TrustedSiteRepository(testScenarioSupport.dataSource);
        jdbcTemplate = new JdbcTemplate(testScenarioSupport.dataSource);

        jdbcTemplate.execute("DELETE FROM trusted_reviews");
        jdbcTemplate.execute("DELETE FROM trusted_sites");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void createInsertsATrustedReviewRow() {
        long boardId = 123;
        int rating = 90;
        TrustedSite trustedSite = trustedSiteRepository.create(new TrustedSite("SUP for the Soul", "http://www.supforthesoul.com/"));
        TrustedReview trustedReviewToCreate = new TrustedReview(boardId, trustedSite.getId(), rating);

        TrustedReview createdTrustedReview = repository.create(trustedReviewToCreate);

        Map<String, Object> foundTrustedReview = jdbcTemplate.queryForMap("SELECT * FROM trusted_reviews WHERE id = ?", createdTrustedReview.getId());
        assertThat(foundTrustedReview.get("id")).isEqualTo(createdTrustedReview.getId());
        assertThat(foundTrustedReview.get("board_id")).isEqualTo(boardId);
        assertThat(foundTrustedReview.get("trusted_site_id")).isEqualTo(trustedSite.getId());
        assertThat(foundTrustedReview.get("rating")).isEqualTo(rating);
    }

    @Test
    public void findByBoardIdReturnsTrustedReviewsWithTrustedSitesOrderedByName() {
        TrustedSite supForTheSoul = trustedSiteRepository.create(new TrustedSite("SUP for the Soul", "http://www.supforthesoul.com/"));
        TrustedSite endlessWaves = trustedSiteRepository.create(new TrustedSite("Endless Waves", "https://endlesswaves.net/"));
        repository.create(new TrustedReview(456, supForTheSoul.getId(), 80));
        repository.create(new TrustedReview(123, supForTheSoul.getId(), 70));
        repository.create(new TrustedReview(123, endlessWaves.getId(), 60));

        List<TrustedReview> trustedReviews = repository.findByBoardId(123);
        assertThat(trustedReviews.size()).isEqualTo(2);
        TrustedReview firstReview = trustedReviews.get(0);
        assertThat(firstReview.getBoardId()).isEqualTo(123);
        assertThat(firstReview.getTrustedSiteId()).isEqualTo(endlessWaves.getId());
        assertThat(firstReview.getRating()).isEqualTo(60);
        assertThat(firstReview.getTrustedSite().getName()).isEqualTo("Endless Waves");
        assertThat(firstReview.getTrustedSite().getUrl()).isEqualTo("https://endlesswaves.net/");

        TrustedReview secondReview = trustedReviews.get(1);
        assertThat(secondReview.getBoardId()).isEqualTo(123);
        assertThat(secondReview.getTrustedSiteId()).isEqualTo(supForTheSoul.getId());
        assertThat(secondReview.getRating()).isEqualTo(70);
        assertThat(secondReview.getTrustedSite().getName()).isEqualTo("SUP for the Soul");
        assertThat(secondReview.getTrustedSite().getUrl()).isEqualTo("http://www.supforthesoul.com/");
    }
}
