DROP DATABASE IF EXISTS construction_dev;
DROP DATABASE IF EXISTS construction_test;

CREATE DATABASE construction_dev;
CREATE DATABASE construction_test;

CREATE USER IF NOT EXISTS 'board_selector'@'localhost'
  IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON *.* TO 'board_selector' @'localhost';
