DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS GLOBAL_SEQ;

CREATE SEQUENCE GLOBAL_SEQ START WITH 100000;

CREATE TABLE users
(
    id         INTEGER DEFAULT GLOBAL_SEQ.nextval PRIMARY KEY,
    name       VARCHAR(255)          NOT NULL,
    email      VARCHAR(255)          NOT NULL,
    password   VARCHAR(255)          NOT NULL,
    registered DATE    DEFAULT now() NOT NULL,
    enabled    BOOL    DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
    ON USERS (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id   INTEGER DEFAULT GLOBAL_SEQ.nextval PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE dishes
(
    id            INTEGER DEFAULT GLOBAL_SEQ.nextval PRIMARY KEY,
    name          VARCHAR(255)          NOT NULL,
    price         DECIMAL               NOT NULL,
    created       DATE    DEFAULT now() NOT NULL,
    restaurant_id INTEGER               NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE votes
(
    id            INTEGER DEFAULT GLOBAL_SEQ.nextval PRIMARY KEY,
    user_id       INTEGER               NOT NULL,
    date          DATE    DEFAULT now() NOT NULL,
    restaurant_id INTEGER               NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE INDEX unique_user_date_idx
    ON votes (date, user_id);





