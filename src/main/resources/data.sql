DELETE FROM users;
DELETE FROM user_roles;
DELETE FROM restaurants;
DELETE FROM dishes;
DELETE FROM votes;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('User2', 'user2@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}password');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_USER', 100001),
       ('ROLE_USER', 100002),
       ('ROLE_ADMIN', 100002);

INSERT INTO RESTAURANTS (NAME)
VALUES ('First'),
       ('Second'),
       ('Diner without dishes');

INSERT INTO DISHES (NAME, PRICE, CREATED, RESTAURANT_ID)

VALUES ('actual dish one', 10, now(), 100003),
       ('old dish one', 5.5, '2019-11-20', 100003),
       ('old dish two', 7.3, '2019-11-22', 100003),
       ('actual dish two', 100, now(), 100004),
       ('actual dish three', 3.2, now(), 100004),
       ('old dish three', 20, '2019-11-20', 100004),
       ('old and cold', 21, '2019-11-20', 100005);

INSERT INTO VOTES (USER_ID, DATE, RESTAURANT_ID)
VALUES (100000, '2019-11-20', 100004),
       (100000, '2020-01-13', 100003),
       (100002, '2019-11-20', 100004),
       (100002, now(), 100005);