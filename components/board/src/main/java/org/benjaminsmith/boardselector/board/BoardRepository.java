package org.benjaminsmith.boardselector.board;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class BoardRepository {
    private JdbcTemplate jdbcTemplate;

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
}
