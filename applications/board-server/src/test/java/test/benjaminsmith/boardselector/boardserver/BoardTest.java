package test.benjaminsmith.boardselector.boardserver;

import com.jayway.jsonpath.DocumentContext;
import org.benjaminsmith.boardselector.board.Board;
import org.benjaminsmith.boardselector.boardserver.App;
import org.benjaminsmith.boardselector.construction.Construction;
import org.benjaminsmith.boardselector.construction.ConstructionRepository;
import org.benjaminsmith.boardselector.manufacturer.Manufacturer;
import org.benjaminsmith.boardselector.manufacturer.ManufacturerRepository;
import org.benjaminsmith.boardselector.testsupport.TestScenarioSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import static com.jayway.jsonpath.JsonPath.parse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BoardTest {
    private ConfigurableApplicationContext context;
    private ManufacturerRepository manufacturerRepository;
    private ConstructionRepository constructionRepository;
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        context = SpringApplication.run(App.class);
        TestScenarioSupport testScenarioSupport = new TestScenarioSupport("board_server_test");
        JdbcTemplate jdbcTemplate = testScenarioSupport.template;
        manufacturerRepository = new ManufacturerRepository(testScenarioSupport.dataSource);
        constructionRepository = new ConstructionRepository(testScenarioSupport.dataSource);

        jdbcTemplate.execute("DELETE FROM boards");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder().rootUri("http://localhost:8182").basicAuthorization("admin", "password");
        restTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @After
    public void tearDown() {
        context.close();
    }

    @Test
    public void testBoards() {
        Manufacturer manufacturer = manufacturerRepository.create(new Manufacturer("Badfish"));
        Construction construction = constructionRepository.create(new Construction("inflatable"));
        Board boardToCreate = new Board("IRS", construction.getId(), manufacturer.getId());

        String createResponse = restTemplate.postForObject("/admin/boards", boardToCreate, String.class);

        DocumentContext boardJson = parse(createResponse);
        assertThat(boardJson.read("$.id", Long.class)).isNotNull();
        assertThat(boardJson.read("$.model", String.class)).isEqualTo("IRS");
        assertThat(boardJson.read("$.constructionId", Long.class)).isEqualTo(construction.getId());
        assertThat(boardJson.read("$.manufacturerId", Long.class)).isEqualTo(manufacturer.getId());
        long boardId = boardJson.read("$.id", Long.class);

        String listReponse = restTemplate.getForObject("/admin/boards", String.class);
        DocumentContext boardsJson = parse(listReponse);
        List<HashMap> boards = boardsJson.read("$");
        assertThat(boards.size()).isEqualTo(1);
        HashMap<String, Object> board = boards.get(0);
        assertThat(new Long((int) board.get("id"))).isEqualTo(boardId);
        assertThat((String) board.get("model")).isEqualTo("IRS");
        assertThat(new Long((int) board.get("constructionId"))).isEqualTo(construction.getId());
        assertThat(new Long((int) board.get("manufacturerId"))).isEqualTo(manufacturer.getId());

    }
}
