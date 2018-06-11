package org.benjaminsmith.boardselector.manufacturer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ManufacturerRepository {
    private JdbcTemplate jdbcTemplate;

    public ManufacturerRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Manufacturer create(Manufacturer manufacturer) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO manufacturers (name) VALUES(?)";

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(),
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, manufacturer.getName());
                return ps;
            }
        }, holder);

        manufacturer.setId(holder.getKey().longValue());
        return manufacturer;
    }
}
