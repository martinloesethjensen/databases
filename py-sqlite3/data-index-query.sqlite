-- DROP TABLE users;

CREATE TABLE  users(
    id INTEGER PRIMARY KEY NOT NULL,
    name TEXT NOT NULL
);

INSERT INTO users (name) values("C");

SELECT * FROM users where id = '1000';

SELECT COUNT(*) FROM users WHERE name = "John";

SELECT * FROM users WHERE name LIKE "J%";

SELECT * FROM users WHERE id = 500000;

UPDATE users SET name = "XXX" WHERE id = 500000;

EXPLAIN QUERY PLAN
SELECT * FROM users WHERE name = "XXX";

CREATE INDEX idx_name ON users (name);

DROP INDEX idx_name;
