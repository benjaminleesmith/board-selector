CREATE TABLE trusted_sites (
  id         BIGINT(20) NOT NULL AUTO_INCREMENT,
  name VARCHAR(128) NOT NULL,

  PRIMARY KEY (id)
)
  ENGINE = innodb
  DEFAULT CHARSET = utf8;