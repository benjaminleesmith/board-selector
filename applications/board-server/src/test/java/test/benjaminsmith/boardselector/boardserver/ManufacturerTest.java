package test.benjaminsmith.boardselector.boardserver;

import com.jayway.jsonpath.DocumentContext;
import org.benjaminsmith.boardselector.construction.App;
import org.benjaminsmith.boardselector.manufacturer.Manufacturer;
import org.benjaminsmith.boardselector.manufacturer.ManufacturerRepository;
import org.benjaminsmith.boardselector.testsupport.TestScenarioSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;

import static com.jayway.jsonpath.JsonPath.parse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ManufacturerTest {
    private TestScenarioSupport testScenarioSupport;
    private JdbcTemplate jdbcTemplate;
    private ManufacturerRepository repository;

    @Before
    public void setup() {
        testScenarioSupport = new TestScenarioSupport("board_server_test");
        jdbcTemplate = testScenarioSupport.template;
        repository = new ManufacturerRepository(testScenarioSupport.dataSource);

        jdbcTemplate.execute("DELETE FROM manufacturers");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void testManufacturer() {
        Manufacturer manufacturer = repository.create(new Manufacturer("Badfish"));

        App.main(new String[]{});

        String response = new RestTemplate().getForObject("http://localhost:8182/admin/manufacturers", String.class);

        DocumentContext manufacturersJson = parse(response);
        ArrayList<HashMap> manufacturers = manufacturersJson.read("$", ArrayList.class);
        assertThat(manufacturers.size()).isEqualTo(1);
        HashMap<String, Object> returnedManufacturer = manufacturers.get(0);
        assertThat((int)returnedManufacturer.get("id")).isEqualTo((int)manufacturer.getId());
        assertThat(returnedManufacturer.get("name")).isEqualTo(manufacturer.getName());
    }
}
