CREATE TABLE printers (
    id            UUID           NOT NULL PRIMARY KEY,
    name          VARCHAR(100)   NOT NULL UNIQUE,
    model         VARCHAR(100)   NOT NULL,
    power_watts   INTEGER        NOT NULL,
    cost_per_hour NUMERIC(10, 2) NOT NULL,
    active        BOOLEAN        NOT NULL
);
