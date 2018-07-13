package org.benjaminsmith.boardselector.trustedsite;

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
public class TrustedSiteRepository {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<TrustedSite> rowMapper = new RowMapper<TrustedSite>() {
        @Override
        public TrustedSite mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TrustedSite(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("url")
            );
        }
    };

    public TrustedSiteRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public TrustedSite create(TrustedSite trustedSiteToCreate) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO trusted_sites (name) VALUES(?)";

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(),
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, trustedSiteToCreate.getName());
                return ps;
            }
        }, holder);

        trustedSiteToCreate.setId(holder.getKey().longValue());
        return trustedSiteToCreate;
    }

    public List<TrustedSite> list() {
        return jdbcTemplate.query("SELECT * FROM trusted_sites ORDER BY name", new Object[] {}, rowMapper);
    }
}
