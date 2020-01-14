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
    name       VARCHAR(255)                 NOT NULL,
    email      VARCHAR(255)                 NOT NULL,
    password   VARCHAR(255)                 NOT NULL,
    registered DATE    DEFAULT CURRENT_DATE NOT NULL,
    enabled    BOOL    DEFAULT TRUE         NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
    ON USERS (email);

INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('User2', 'user2@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}password');

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

INSERT INTO USER_ROLES (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_USER', 100001),
       ('ROLE_USER', 100002),
       ('ROLE_ADMIN', 100002);

CREATE TABLE restaurants
(
    id   INTEGER DEFAULT GLOBAL_SEQ.nextval PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
);

INSERT INTO RESTAURANTS (NAME)
VALUES ('First'),
       ('Second'),
       ('Diner without dishes');

CREATE TABLE dishes
(
    id            INTEGER DEFAULT GLOBAL_SEQ.nextval PRIMARY KEY,
    name          VARCHAR(255)                 NOT NULL,
    price         DECIMAL                      NOT NULL,
    created       DATE    DEFAULT CURRENT_DATE NOT NULL,
    restaurant_id INTEGER                      NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

INSERT INTO DISHES (NAME, PRICE, CREATED, RESTAURANT_ID)

VALUES ('actual dish one', 10, now(), 100003),
       ('old dish one', 5.5, '2019-11-20', 100003),
       ('old dish two', 7.3, '2019-11-22', 100003),
       ('actual dish two', 100, now(), 100004),
       ('actual dish three', 3.2, now(), 100004),
       ('old dish three', 20, '2019-11-20', 100004),
       ('old and cold', 21, '2019-11-20', 100005);

CREATE TABLE votes
(
    id            INTEGER DEFAULT GLOBAL_SEQ.nextval PRIMARY KEY,
    user_id       INTEGER               NOT NULL,
    date          DATE    DEFAULT now() NOT NULL,
    restaurant_id INTEGER               NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE INDEX votes_user_idx
    ON votes (user_id);

INSERT INTO VOTES (USER_ID, DATE, RESTAURANT_ID)
VALUES (100000, '2019-11-20', 100004),
       (100000, now(), 100003),
       (100002, '2019-11-20', 100004),
       (100002, now(), 100005);




