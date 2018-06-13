package org.benjaminsmith.boardselector.trustedreview;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class TrustedReviewRepository {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<TrustedReview> rowMapper = new RowMapper<TrustedReview>() {
        @Override
        public TrustedReview mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            return new TrustedReview(
                    resultSet.getLong("id"),
                    resultSet.getLong("board_id"),
                    resultSet.getLong("trusted_site_id"),
                    resultSet.getInt("rating"),
                    resultSet.getString("name")
            );
        }
    };

    public TrustedReviewRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public TrustedReview create(TrustedReview trustedReviewToCreate) {
        final String sql = "INSERT INTO trusted_reviews (board_id, trusted_site_id, rating) VALUES(?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setLong(1, trustedReviewToCreate.getBoardId());
                ps.setLong(2, trustedReviewToCreate.getTrustedSiteId());
                ps.setInt(3, trustedReviewToCreate.getRating());
                return ps;
            }
        }, holder);

        trustedReviewToCreate.setId(holder.getKey().longValue());
        return trustedReviewToCreate;
    }

    public List<TrustedReview> findByBoardId(long boardId) {
        return jdbcTemplate.query(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement ps = con.prepareStatement("SELECT * FROM trusted_reviews INNER JOIN trusted_sites ON trusted_reviews.trusted_site_id = trusted_sites.id WHERE board_id = ? ORDER BY trusted_sites.name");
                        ps.setLong(1, boardId);
                        return ps;
                    }
                },
                rowMapper
        );
    }
}
