package org.benjaminsmith.boardselector.manufacturer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class ManufacturerRepository {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Manufacturer> rowMapper = new RowMapper<Manufacturer>() {
        @Override
        public Manufacturer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Manufacturer(rs.getLong("id"), rs.getString("name"));
        }
    };

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

    public List<Manufacturer> list() {
        return jdbcTemplate.query("SELECT * FROM manufacturers", new Object[] {}, rowMapper);
    }
}
