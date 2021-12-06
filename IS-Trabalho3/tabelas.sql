BEGIN;
DROP TABLE IF EXISTS utilizador;
CREATE TABLE IF NOT EXISTS utilizador (
    id	serial NOT NULL,
    name VARCHAR(512) UNIQUE NOT NULL,
    type int NOT NULL,
    managerid int,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS currencies (
    id	serial NOT NULL,
    currency VARCHAR(512) UNIQUE NOT NULL,
    exchange float NOT NULL,
    PRIMARY KEY(id)
    );


COMMIT;