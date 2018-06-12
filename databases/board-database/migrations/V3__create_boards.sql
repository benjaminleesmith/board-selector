CREATE TABLE boards (
  id         BIGINT(20) NOT NULL AUTO_INCREMENT,
  model VARCHAR(128) NOT NULL,
  manufacturer_id BIGINT(20) NOT NULL,
  construction_id BIGINT(20) NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (manufacturer_id)
        REFERENCES manufacturers(id)
        ON DELETE CASCADE,
  FOREIGN KEY (construction_id)
        REFERENCES constructions(id)
        ON DELETE CASCADE
)
  ENGINE = innodb
  DEFAULT CHARSET = utf8;