package test.benjaminsmith.boardselector.trustedreview;

import org.benjaminsmith.boardselector.trustedreview.TrustedReview;
import org.benjaminsmith.boardselector.trustedreview.TrustedReviewController;
import org.benjaminsmith.boardselector.trustedreview.TrustedReviewRepository;
import org.benjaminsmith.boardselector.trustedsite.TrustedSite;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TrustedReviewControllerTest {
    private TrustedReviewController controller;
    private TrustedReviewRepository repository;

    @Before
    public void setup() {
        repository = mock(TrustedReviewRepository.class);
        controller = new TrustedReviewController(repository);
    }

    @Test
    public void listReturnsTrustedReviewsForABoard() {
        List<TrustedReview> expectedTrustedReviews = asList(
                new TrustedReview(1, 123, 999, 90, "SUP for the Soul"),
                new TrustedReview(2, 123, 888, 80, "Endless Waves")
        );
        doReturn(expectedTrustedReviews).when(repository).findByBoardId(123);

        ResponseEntity<List<TrustedReview>> trustedReviews = controller.list(123L);

        verify(repository).findByBoardId(123);
        assertThat(trustedReviews.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(trustedReviews.getBody()).isEqualTo(expectedTrustedReviews);
    }
}
