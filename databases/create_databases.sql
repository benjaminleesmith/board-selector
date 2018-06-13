DROP DATABASE IF EXISTS board_server_dev;
DROP DATABASE IF EXISTS board_server_test;

DROP DATABASE IF EXISTS trusted_review_server_dev;
DROP DATABASE IF EXISTS trusted_review_server_test;

CREATE DATABASE board_server_dev;
CREATE DATABASE board_server_test;

CREATE DATABASE trusted_review_server_dev;
CREATE DATABASE trusted_review_server_test;

CREATE USER IF NOT EXISTS 'board_selector'@'localhost'
  IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON *.* TO 'board_selector' @'localhost';
