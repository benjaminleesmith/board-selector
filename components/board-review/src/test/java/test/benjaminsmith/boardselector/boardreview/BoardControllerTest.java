package test.benjaminsmith.boardselector.boardreview;

import org.benjaminsmith.boardselector.boardreview.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BoardControllerTest {
    private BoardController controller;
    private BoardClient boardClient;
    private TrustedReviewClient trustedReviewClient;

    @Before
    public void setup() {
        boardClient = mock(BoardClient.class);
        trustedReviewClient = mock(TrustedReviewClient.class);
        controller = new BoardController(boardClient, trustedReviewClient);
    }

    @Test
    public void listReturnsBoardsAndReviews() {
        Board board = new Board(1L, "IRS", 2L, 3L);
        Board[] boards = {board};
        TrustedReview[] trustedReviews = {new TrustedReview(4L, 5L, 77)};
        TrustedSite[] trustedSites = { new TrustedSite(5L, "SUP for the Soul") };

        doReturn(boards).when(boardClient).getBoards();
        doReturn(trustedReviews).when(trustedReviewClient).getTrustedReviewsForBoard(1L);
        doReturn(trustedSites).when(trustedReviewClient).getTrustedSites();

        ResponseEntity<AllBoards> responseEntity = controller.list();

        verify(boardClient).getBoards();
        verify(trustedReviewClient).getTrustedReviewsForBoard(1L);
        verify(trustedReviewClient).getTrustedSites();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        board.setTrustedReviews(trustedReviews);
        AllBoards allBoards = new AllBoards(boards, trustedSites);
        assertThat(responseEntity.getBody()).isEqualTo(allBoards);
    }
}
