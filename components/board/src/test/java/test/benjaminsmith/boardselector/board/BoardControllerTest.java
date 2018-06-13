package test.benjaminsmith.boardselector.board;

import org.benjaminsmith.boardselector.board.Board;
import org.benjaminsmith.boardselector.board.BoardController;
import org.benjaminsmith.boardselector.board.BoardRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BoardControllerTest {
    private BoardRepository repository;
    private BoardController controller;

    @Before
    public void setup() {
        repository = mock(BoardRepository.class);
        controller = new BoardController(repository);
    }

    @Test
    public void testCreate() {
        int manufacturerId = 444;
        int constructionId = 555;
        Board boardToCreate = new Board("IRS", constructionId, manufacturerId);
        Board expectedResult = new Board(1, "IRS", constructionId, manufacturerId);
        doReturn(expectedResult).when(repository).create(any(Board.class));

        ResponseEntity response = controller.create(boardToCreate);

        verify(repository).create(boardToCreate);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expectedResult);
    }

    @Test
    public void testList() {
        List<Board> expectedResult = asList(
                new Board(1, "IRS", 123, 456)
        );
        doReturn(expectedResult).when(repository).list();

        ResponseEntity response = controller.list();

        verify(repository).list();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResult);
    }
}
