PRAGMA foreign_keys = ON;

CREATE TABLE device (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(40),
    desc TEXT,
    ip_address VARCHAR(25),
    port VARCHAR(5),
    colour VARCHAR(15)
);

CREATE TABLE feature (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(50),
    topic VARCHAR(50),
    type INTEGER,
    value VARCHAR(100),
    device_id INTEGER,
    FOREIGN KEY (device_id) REFERENCES device (id)
);
