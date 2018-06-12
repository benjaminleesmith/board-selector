package test.benjaminsmith.boardselector.boardserver;

import com.jayway.jsonpath.DocumentContext;
import org.benjaminsmith.boardselector.construction.App;
import org.benjaminsmith.boardselector.construction.Construction;
import org.benjaminsmith.boardselector.construction.ConstructionRepository;
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
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;

import static com.jayway.jsonpath.JsonPath.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = RANDOM_PORT)
public class ConstructionTest {
    private TestScenarioSupport testScenarioSupport;
    private JdbcTemplate jdbcTemplate;
    private ConstructionRepository repository;

    @LocalServerPort
    private String port;
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        testScenarioSupport = new TestScenarioSupport("board_server_test");
        jdbcTemplate = testScenarioSupport.template;
        repository = new ConstructionRepository(testScenarioSupport.dataSource);

        jdbcTemplate.execute("DELETE FROM constructions");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder().rootUri("http://localhost:"+port);
        restTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    public void testConstruction() {
        Construction construction = repository.create(new Construction("inflatable"));

        String response = restTemplate.getForObject("/admin/constructions", String.class);

        DocumentContext constructionsJson = parse(response);
        ArrayList<HashMap> constructions = constructionsJson.read("$", ArrayList.class);

        assertThat(constructions.size()).isEqualTo(1);
        HashMap constructionMap = constructions.get(0);
        assertThat((int)constructionMap.get("id")).isEqualTo((int)construction.getId());
        assertThat(constructionMap.get("name")).isEqualTo(construction.getName());
    }
}
