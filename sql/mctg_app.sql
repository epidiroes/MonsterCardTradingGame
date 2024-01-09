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

DROP TABLE battles;
CREATE TABLE IF NOT EXISTS battles (
    id VARCHAR(255) PRIMARY KEY,
    player1 VARCHAR(255)  REFERENCES users(id),
    player2 VARCHAR(255)  REFERENCES users(id),
    winner VARCHAR(255)  REFERENCES users(id),
    log VARCHAR,
    open BOOLEAN
);

CREATE TABLE IF NOT EXISTS stats (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) REFERENCES users(id),
    elo INT,
    games_played INT,
    games_won INT
);

CREATE TABLE IF NOT EXISTS scoreboard (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) REFERENCES users(id),
    stats_id VARCHAR(255) REFERENCES stats(id)
);

DROP TABLE scoreboard;
DROP TABLE stats;
DROP TABLE battles;
DROP TABLE decks;
DROP TABLE packages;
DROP TABLE users;
DROP TABLE cards;
