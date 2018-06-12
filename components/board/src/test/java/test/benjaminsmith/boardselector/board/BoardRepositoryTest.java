package test.benjaminsmith.boardselector.board;

import org.benjaminsmith.boardselector.board.Board;
import org.benjaminsmith.boardselector.board.BoardRepository;
import org.benjaminsmith.boardselector.construction.Construction;
import org.benjaminsmith.boardselector.construction.ConstructionRepository;
import org.benjaminsmith.boardselector.manufacturer.Manufacturer;
import org.benjaminsmith.boardselector.manufacturer.ManufacturerRepository;
import org.benjaminsmith.boardselector.testsupport.TestScenarioSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BoardRepositoryTest {
    private TestScenarioSupport testScenarioSupport;
    private JdbcTemplate jdbcTemplate;
    private BoardRepository repository;
    private ConstructionRepository constructionRepository;
    private ManufacturerRepository manufacturerRepositry;

    @Before
    public void setUp() {
        testScenarioSupport = new TestScenarioSupport("board_server_test");
        jdbcTemplate = testScenarioSupport.template;
        repository = new BoardRepository(testScenarioSupport.dataSource);
        constructionRepository = new ConstructionRepository(testScenarioSupport.dataSource);
        manufacturerRepositry = new ManufacturerRepository(testScenarioSupport.dataSource);

        jdbcTemplate.execute("DELETE FROM boards");
        jdbcTemplate.execute("DELETE FROM constructions");
        jdbcTemplate.execute("DELETE FROM manufacturers");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void testCreateInsertsABoardRow() {
        Construction construction = constructionRepository.create(new Construction("Inflatable"));
        Manufacturer manufacturer = manufacturerRepositry.create(new Manufacturer("Badfish"));

        Board board = new Board("IRS", construction.getId(), manufacturer.getId());

        Board createdBoard = repository.create(board);

        Map<String, Object> foundBoard = jdbcTemplate.queryForMap("SELECT * FROM boards WHERE id = ?", createdBoard.getId());

        assertThat(foundBoard.get("id")).isEqualTo(createdBoard.getId());
        assertThat(foundBoard.get("model")).isEqualTo(createdBoard.getModel());
        assertThat(foundBoard.get("manufacturer_id")).isEqualTo(manufacturer.getId());
        assertThat(foundBoard.get("construction_id")).isEqualTo(construction.getId());
    }

    @Test
    public void testListReturnsAllBoards() {
        Construction construction = constructionRepository.create(new Construction("Inflatable"));
        Manufacturer manufacturer = manufacturerRepositry.create(new Manufacturer("Badfish"));
        Board createdBoard = repository.create(new Board("IRS", construction.getId(), manufacturer.getId()));

        List<Board> boards = repository.list();

        assertThat(boards.size()).isEqualTo(1);
        Board board = boards.get(0);
        assertThat(board.getId()).isEqualTo(createdBoard.getId());
        assertThat(board.getConstructionId()).isEqualTo(construction.getId());
        assertThat(board.getManufacturerId()).isEqualTo(manufacturer.getId());
        assertThat(board.getModel()).isEqualTo("IRS");
    }
}
