package test.benjaminsmith.boardselector.trustedsite;

import org.benjaminsmith.boardselector.trustedsite.TrustedSite;
import org.benjaminsmith.boardselector.trustedsite.TrustedSiteController;
import org.benjaminsmith.boardselector.trustedsite.TrustedSiteRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class TrustedSiteControllerTest {
    private TrustedSiteController controller;
    private TrustedSiteRepository repository;

    @Before
    public void setup() {
        repository = mock(TrustedSiteRepository.class);
        controller = new TrustedSiteController(repository);
    }

    @Test
    public void list() {
        List<TrustedSite> expectedResult = asList(
                new TrustedSite(1, "SUP for the Soul", "http://www.supforthesoul.com/"),
                new TrustedSite(2, "Endless Waves", "https://endlesswaves.net/")
        );
        doReturn(expectedResult).when(repository).list();

        ResponseEntity<List<TrustedSite>> responseEntity = controller.list();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(expectedResult);
    }
}
