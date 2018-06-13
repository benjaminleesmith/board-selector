CREATE TABLE trusted_reviews (
  id         BIGINT(20) NOT NULL AUTO_INCREMENT,
  board_id BIGINT(20) NOT NULL,
  trusted_site_id BIGINT(20) NOT NULL,
  rating INT NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (trusted_site_id)
        REFERENCES trusted_sites(id)
        ON DELETE CASCADE
)
  ENGINE = innodb
  DEFAULT CHARSET = utf8;