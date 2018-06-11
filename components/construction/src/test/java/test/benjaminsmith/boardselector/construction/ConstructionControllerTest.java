package test.benjaminsmith.boardselector.construction;

import org.benjaminsmith.boardselector.construction.Construction;
import org.benjaminsmith.boardselector.construction.ConstructionController;
import org.benjaminsmith.boardselector.construction.ConstructionRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConstructionControllerTest {
    private ConstructionRepository repository;
    private ConstructionController controller;

    @Before
    public void setup() {
        repository = mock(ConstructionRepository.class);
        controller = new ConstructionController(repository);
    }

    @Test
    public void testCreate() {
        Construction constructionToCreate = new Construction("inflatable");
        Construction expectedResult = new Construction(1L, "inflatable");
        doReturn(expectedResult).when(repository).create(any(Construction.class));

        ResponseEntity response = controller.create(constructionToCreate);

        verify(repository).create(constructionToCreate);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expectedResult);
    }

    @Test
    public void testList() {
        List<Construction> constructions = asList(
                new Construction(1L, "inflatable"),
                new Construction(2L, "foamie")
        );
        doReturn(constructions).when(repository).list();

        ResponseEntity<List<Construction>> response = controller.list();

        verify(repository).list();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(constructions);
    }
}
