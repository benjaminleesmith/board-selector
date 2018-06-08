package test.benjaminsmith.boardselector.construction;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.benjaminsmith.boardselector.construction.Construction;
import org.benjaminsmith.boardselector.construction.ConstructionRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;


public class ConstructionRepositoryTest {
    private ConstructionRepository repository;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(System.getenv("SPRING_DATASOURCE_URL"));

        repository = new ConstructionRepository(dataSource);

        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("DELETE FROM constructions");

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void createInsertsAConstructionRecord() {
        Construction construction = new Construction("inflatable");

        repository.create(construction);

        Map<String, Object> foundConstruction = jdbcTemplate.queryForMap("SELECT * FROM constructions WHERE id = ?", construction.getId());

        assertThat(foundConstruction.get("id")).isEqualTo(construction.getId());
        assertThat(foundConstruction.get("name")).isEqualTo("inflatable");
    }

    @Test
    public void listReturnsAllTimeEntriesInAlphabeticalOrder() {
        jdbcTemplate.execute("INSERT INTO constructions (id, name) VALUES (777, 'inflatable'), (888, 'foamie')");

        List<Construction> constructions = repository.list();
        assertThat(constructions.size()).isEqualTo(2);

        Construction construction = constructions.get(0);
        assertThat(construction.getId()).isEqualTo(888);
        assertThat(construction.getName()).isEqualTo("foamie");

        construction = constructions.get(1);
        assertThat(construction.getId()).isEqualTo(777);
        assertThat(construction.getName()).isEqualTo("inflatable");
    }
}
