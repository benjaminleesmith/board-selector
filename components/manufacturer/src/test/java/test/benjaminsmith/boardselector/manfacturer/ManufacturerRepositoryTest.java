package test.benjaminsmith.boardselector.manfacturer;

import org.benjaminsmith.boardselector.manufacturer.Manufacturer;
import org.benjaminsmith.boardselector.manufacturer.ManufacturerRepository;
import org.benjaminsmith.boardselector.testsupport.TestScenarioSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;


public class ManufacturerRepositoryTest {
    private ManufacturerRepository repository;
    private TestScenarioSupport testScenarioSupport;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        testScenarioSupport = new TestScenarioSupport("board_server_test");
        jdbcTemplate = testScenarioSupport.template;
        repository = new ManufacturerRepository(testScenarioSupport.dataSource);

        jdbcTemplate.execute("DELETE FROM manufacturers");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void testCreateInsertsAManufacturerRecord() {
        Manufacturer manufacturer = new Manufacturer("Badfish");

        Manufacturer createdManufacturer = repository.create(manufacturer);

        Map<String, Object> foundManufacturer = jdbcTemplate.queryForMap("SELECT * FROM manufacturers WHERE id = ?", createdManufacturer.getId());
        assertThat(foundManufacturer.get("id")).isEqualTo(createdManufacturer.getId());
        assertThat(foundManufacturer.get("name")).isEqualTo("Badfish");
    }
}
