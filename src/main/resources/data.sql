INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3);

INSERT INTO restaurant (name)
VALUES ('Italian Place'),
       ('Sushi City'),
       ('Burger House');

INSERT INTO dish (name, price, menu_date, restaurant_id)
VALUES ('Pizza Margherita', 500, CURRENT_DATE, 1),
       ('Spaghetti', 400, CURRENT_DATE, 1),
       ('Caesar Salad', 300, CURRENT_DATE, 1),
       ('Philadelphia', 700, CURRENT_DATE, 2),
       ('Miso soup', 250, CURRENT_DATE, 2),
       ('Old pizza', 450, DATEADD('DAY', -1, CURRENT_DATE), 1);

INSERT INTO vote (vote_date, user_id, restaurant_id)
VALUES (DATEADD('DAY', -1, CURRENT_DATE), 1, 1);