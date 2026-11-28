CREATE TABLE materials (
    id           UUID           NOT NULL PRIMARY KEY,
    name         VARCHAR(100)   NOT NULL UNIQUE,
    type         VARCHAR(20)    NOT NULL,
    price_per_kg NUMERIC(10, 2) NOT NULL,
    active       BOOLEAN        NOT NULL
);
