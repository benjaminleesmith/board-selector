DROP DATABASE IF EXISTS board_selector_dev;
DROP DATABASE IF EXISTS board_selector_test;

CREATE DATABASE board_selector_dev;
CREATE DATABASE board_selector_test;

CREATE USER IF NOT EXISTS 'board_selector'@'localhost'
  IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON board_selector_dev.* TO 'board_selector' @'localhost';
GRANT ALL PRIVILEGES ON board_selector_test.* TO 'board_selector' @'localhost';
