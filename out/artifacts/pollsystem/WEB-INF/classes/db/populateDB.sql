DELETE
FROM USER_ROLES;
DELETE
FROM VOTES;
DELETE
FROM DISHES;
DELETE
FROM RESTAURANTS;
DELETE
FROM USERS;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO USERS ( name, email, password)
VALUES ('User1', 'user@yandex.ru', 'password'),
       ('User2', 'user2@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_USER', 100001),
       ('ROLE_ADMIN', 100002);

INSERT INTO RESTAURANTS (NAME, ADMIN_ID)
VALUES ('McDuck', 100002),
       ('McWhat', 100002);

INSERT INTO DISHES (NAME, PRICE, CREATED, RESTAURANT_ID)
VALUES ('foo', 10, '2019-11-20 10:00:00', 100003),
       ('bar', 10, '2019-11-22 10:00:00', 100003),
       ('liver of census taker', 10, '2019-11-21 10:00:00', 100004),
       ('fava beans', 10, '2019-11-20 12:00:00', 100004),
       ('nice chianti', 10, '2019-11-20 18:00:00', 100004);

INSERT INTO VOTES (USER_ID, DATE_TIME, RESTAURANT_ID)
VALUES (100000, '2019-11-20 11:00:00', 100004),
       (100001, '2019-11-20 11:00:00', 100004);



