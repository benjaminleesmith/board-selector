package org.benjaminsmith.boardselector.trustedsite;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TrustedSiteRepository {
    private JdbcTemplate jdbcTemplate;

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
}
