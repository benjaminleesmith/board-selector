package org.benjaminsmith.boardselector.board;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class BoardRepository {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Board> boardRowMapper = new RowMapper<Board>() {
        @Override
        public Board mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            return new Board(
                    resultSet.getLong("id"),
                    resultSet.getString("model"),
                    resultSet.getLong("construction_id"),
                    resultSet.getLong("manufacturer_id")
            );
        }
    };

    public BoardRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Board create(Board board) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO boards (model, construction_id, manufacturer_id) VALUES(?, ?, ?)";

        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(),
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, board.getModel());
                ps.setLong(2, board.getConstructionId());
                ps.setLong(3, board.getManufacturerId());
                return ps;
            }
        }, holder);

        board.setId(holder.getKey().longValue());
        return board;
    }

    public List<Board> list() {
        return jdbcTemplate.query("SELECT * FROM boards", new Object[] {}, boardRowMapper);
    }
}
