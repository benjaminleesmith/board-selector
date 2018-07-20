package test.benjaminsmith.boardselector.trustedsite;

import org.benjaminsmith.boardselector.testsupport.TestScenarioSupport;
import org.benjaminsmith.boardselector.trustedsite.TrustedSite;
import org.benjaminsmith.boardselector.trustedsite.TrustedSiteRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TrustedSiteRepositoryTest {
    private TrustedSiteRepository repository;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        TestScenarioSupport testScenarioSupport = new TestScenarioSupport("trusted_review_server_test");
        repository = new TrustedSiteRepository(testScenarioSupport.dataSource);
        jdbcTemplate = new JdbcTemplate(testScenarioSupport.dataSource);

        jdbcTemplate.execute("DELETE FROM trusted_sites");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void createInsertsATrustedReviewSiteRow() {
        TrustedSite trustedSiteToCreate = new TrustedSite("SUP for the Soul", "http://www.supforthesoul.com/");

        TrustedSite createdTrustedSite = repository.create(trustedSiteToCreate);

        Map<String, Object> foundTrustedSite = jdbcTemplate.queryForMap("SELECT * FROM trusted_sites WHERE id = ?", createdTrustedSite.getId());
        assertThat(foundTrustedSite.get("id")).isEqualTo(createdTrustedSite.getId());
        assertThat(foundTrustedSite.get("name")).isEqualTo("SUP for the Soul");
        assertThat(foundTrustedSite.get("url")).isEqualTo("http://www.supforthesoul.com/");
    }

    @Test
    public void listReturnsAllTrustedSitesOrderedAlphabetically() {
        TrustedSite createdSupForTheSoul = repository.create(new TrustedSite("SUP for the Soul", "http://www.supforthesoul.com/"));
        TrustedSite createdEndlessWaves = repository.create(new TrustedSite("Endless Waves", "https://endlesswaves.net/"));

        List<TrustedSite> trustedSites = repository.list();
        assertThat(trustedSites.size()).isEqualTo(2);
        TrustedSite foundEndlessWaves = trustedSites.get(0);
        assertThat(foundEndlessWaves.getId()).isEqualTo(createdEndlessWaves.getId());
        assertThat(foundEndlessWaves.getName()).isEqualTo("Endless Waves");
        TrustedSite foundSupForTheSoul = trustedSites.get(1);
        assertThat(foundSupForTheSoul.getId()).isEqualTo(createdSupForTheSoul.getId());
        assertThat(foundSupForTheSoul.getName()).isEqualTo("SUP for the Soul");
    }
}
