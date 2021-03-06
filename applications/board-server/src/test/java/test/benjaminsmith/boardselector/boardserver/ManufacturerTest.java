package test.benjaminsmith.boardselector.boardserver;

import com.jayway.jsonpath.DocumentContext;
import org.benjaminsmith.boardselector.boardserver.App;
import org.benjaminsmith.boardselector.manufacturer.Manufacturer;
import org.benjaminsmith.boardselector.manufacturer.ManufacturerRepository;
import org.benjaminsmith.boardselector.testsupport.TestScenarioSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;

import static com.jayway.jsonpath.JsonPath.parse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = RANDOM_PORT)
public class ManufacturerTest {
    private TestScenarioSupport testScenarioSupport;
    private JdbcTemplate jdbcTemplate;
    private ManufacturerRepository repository;

    @LocalServerPort
    private String port;
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        testScenarioSupport = new TestScenarioSupport("board_server_test");
        jdbcTemplate = testScenarioSupport.template;
        repository = new ManufacturerRepository(testScenarioSupport.dataSource);

        jdbcTemplate.execute("DELETE FROM manufacturers");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        RestTemplateBuilder builder = new RestTemplateBuilder().rootUri("http://localhost:"+port);
        restTemplate = new TestRestTemplate(builder);
    }

    @Test
    public void testManufacturer() {
        Manufacturer manufacturer = repository.create(new Manufacturer("Badfish"));

        String response = restTemplate.getForObject("/admin/manufacturers", String.class);

        DocumentContext manufacturersJson = parse(response);
        ArrayList<HashMap> manufacturers = manufacturersJson.read("$", ArrayList.class);
        assertThat(manufacturers.size()).isEqualTo(1);
        HashMap<String, Object> returnedManufacturer = manufacturers.get(0);
        assertThat((int)returnedManufacturer.get("id")).isEqualTo((int)manufacturer.getId());
        assertThat(returnedManufacturer.get("name")).isEqualTo(manufacturer.getName());
    }
}
