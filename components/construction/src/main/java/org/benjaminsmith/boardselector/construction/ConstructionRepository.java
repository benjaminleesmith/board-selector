package org.benjaminsmith.boardselector.construction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ConstructionRepository {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Construction> constructionRowMapper = new RowMapper<Construction>() {
        public Construction mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            return new Construction(resultSet.getLong("id"), resultSet.getString("name"));
        }
    };

    public ConstructionRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Construction create(Construction construction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement("INSERT INTO constructions (name) VALUES (?)", new String[] {"id"});
                        ps.setString(1, construction.getName());
                        return ps;
                    }
                },
                keyHolder);
        construction.setId(keyHolder.getKey().longValue());
        return construction;
    }

    public List<Construction> list() {
        return jdbcTemplate.query("SELECT * FROM constructions ORDER BY name", new Object[] {}, constructionRowMapper);
    }
}
