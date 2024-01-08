CREATE DATABASE mctgdb;
DROP DATABASE mctgdb;

\c mctgdb;

CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    coins INT NOT NULL,
    name VARCHAR(255),
    bio VARCHAR(255),
    image VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS cards (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    damage INT NOT NULL
);

CREATE TABLE IF NOT EXISTS packages (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) REFERENCES users(id),
    card1_id VARCHAR(255) REFERENCES cards(id),
    card2_id VARCHAR(255) REFERENCES cards(id),
    card3_id VARCHAR(255) REFERENCES cards(id),
    card4_id VARCHAR(255) REFERENCES cards(id),
    card5_id VARCHAR(255) REFERENCES cards(id),
    CONSTRAINT unique_cards UNIQUE (card1_id, card2_id, card3_id, card4_id, card5_id)
);

CREATE TABLE IF NOT EXISTS decks (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) REFERENCES users(id),
    card1_id VARCHAR(255) REFERENCES cards(id),
    card2_id VARCHAR(255) REFERENCES cards(id),
    card3_id VARCHAR(255) REFERENCES cards(id),
    card4_id VARCHAR(255) REFERENCES cards(id),
    CONSTRAINT unique_deck_cards UNIQUE (card1_id, card2_id, card3_id, card4_id)
);

CREATE TABLE IF NOT EXISTS battles (
    id VARCHAR(255) PRIMARY KEY,
    player1 VARCHAR(255)  REFERENCES users(id),
    player2 VARCHAR(255)  REFERENCES users(id),
    player1_score INT,
    player2_score INT,
    winner VARCHAR(255)  REFERENCES users(id),
    log VARCHAR(1500),
    open BOOLEAN
);

DROP TABLE battles;
DROP TABLE decks;
DROP TABLE packages;
DROP TABLE users;
DROP TABLE cards;
