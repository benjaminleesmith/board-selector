package test.benjaminsmith.boardselector.boardserver;

import com.jayway.jsonpath.DocumentContext;
import org.benjaminsmith.boardselector.construction.App;
import org.benjaminsmith.boardselector.construction.Construction;
import org.benjaminsmith.boardselector.construction.ConstructionRepository;
import org.benjaminsmith.boardselector.testsupport.TestScenarioSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;

import static com.jayway.jsonpath.JsonPath.parse;
import static org.assertj.core.api.Assertions.assertThat;

public class ConstructionTest {
    private TestScenarioSupport testScenarioSupport;
    private JdbcTemplate jdbcTemplate;
    private ConstructionRepository repository;

    @Before
    public void setUp() {
        testScenarioSupport = new TestScenarioSupport("board_server_test");
        jdbcTemplate = testScenarioSupport.template;
        repository = new ConstructionRepository(testScenarioSupport.dataSource);

        jdbcTemplate.execute("DELETE FROM constructions");

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void testConstruction() {
        Construction construction = repository.create(new Construction("inflatable"));
        App.main(new String[]{});

        String response = new RestTemplate().getForObject("http://localhost:8182/admin/constructions", String.class);

        DocumentContext constructionsJson = parse(response);
        ArrayList<HashMap> constructions = constructionsJson.read("$", ArrayList.class);

        assertThat(constructions.size()).isEqualTo(1);
        HashMap constructionMap = constructions.get(0);
        assertThat((int)constructionMap.get("id")).isEqualTo((int)construction.getId());
        assertThat(constructionMap.get("name")).isEqualTo(construction.getName());
    }
}
