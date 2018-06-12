package test.benjaminsmith.boardselector.boardserver;

import com.jayway.jsonpath.DocumentContext;
import org.benjaminsmith.boardselector.board.Board;
import org.benjaminsmith.boardselector.board.BoardRepository;
import org.benjaminsmith.boardselector.construction.App;
import org.benjaminsmith.boardselector.construction.Construction;
import org.benjaminsmith.boardselector.construction.ConstructionRepository;
import org.benjaminsmith.boardselector.manufacturer.Manufacturer;
import org.benjaminsmith.boardselector.manufacturer.ManufacturerRepository;
import org.benjaminsmith.boardselector.testsupport.TestScenarioSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.Document;
import java.util.TimeZone;

import static com.jayway.jsonpath.JsonPath.parse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BoardTest {
    private TestScenarioSupport testScenarioSupport;
    private JdbcTemplate jdbcTemplate;
    private BoardRepository boardRepository;
    private ManufacturerRepository manufacturerRepository;
    private ConstructionRepository constructionRepository;

    @Before
    public void setup() {
        testScenarioSupport = new TestScenarioSupport("board_server_test");
        jdbcTemplate = testScenarioSupport.template;
        boardRepository = new BoardRepository(testScenarioSupport.dataSource);
        manufacturerRepository = new ManufacturerRepository(testScenarioSupport.dataSource);
        constructionRepository = new ConstructionRepository(testScenarioSupport.dataSource);

        jdbcTemplate.execute("DELETE FROM boards");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void testBoards() {
        App.main(new String[]{});

        Manufacturer manufacturer = manufacturerRepository.create(new Manufacturer("Badfish"));
        Construction construction = constructionRepository.create(new Construction("inflatable"));
        Board boardToCreate = new Board("IRS", construction.getId(), manufacturer.getId());

        String response = new RestTemplate().postForObject("http://localhost:8182/admin/boards", boardToCreate, String.class);

        DocumentContext boardJson = parse(response);
        assertThat(boardJson.read("$.model", String.class)).isEqualTo("IRS");
        assertThat(boardJson.read("$.constructionId", Long.class)).isEqualTo(construction.getId());
        assertThat(boardJson.read("$.manufacturerId", Long.class)).isEqualTo(manufacturer.getId());
    }
}
