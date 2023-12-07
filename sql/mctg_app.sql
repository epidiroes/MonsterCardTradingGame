CREATE DATABASE mctgdb;
DROP DATABASE mctgdb;

\c mctgdb;

CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    token VARCHAR(255)
);

DROP TABLE users;