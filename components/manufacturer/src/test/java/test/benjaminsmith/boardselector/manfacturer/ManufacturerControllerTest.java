package test.benjaminsmith.boardselector.manfacturer;

import org.benjaminsmith.boardselector.manufacturer.Manufacturer;
import org.benjaminsmith.boardselector.manufacturer.ManufacturerController;
import org.benjaminsmith.boardselector.manufacturer.ManufacturerRepository;
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

public class ManufacturerControllerTest {
    private ManufacturerController controller;
    private ManufacturerRepository repository;

    @Before
    public void setup() {
        repository = mock(ManufacturerRepository.class);
        controller = new ManufacturerController(repository);
    }

    @Test
    public void listReturnsAllBoards() {
        List<Manufacturer> manufacturers = asList(
                new Manufacturer("Badfish"),
                new Manufacturer("Hala")
        );
        doReturn(manufacturers).when(repository).list();

        ResponseEntity<List<Manufacturer>> responseEntity = controller.list();

        verify(repository).list();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(manufacturers);
    }
}
