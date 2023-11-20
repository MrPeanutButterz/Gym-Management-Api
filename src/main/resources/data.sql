INSERT INTO authorities (email, authority)
VALUES ('aron@api.nl', 'ROLE_ADMIN'),
       ('barry@api.nl', 'ROLE_TRAINER'),
       ('charon@api.nl', 'ROLE_MEMBER');


insert into memberships (name, contract_length_in_week, price_Per_Month)
values ('SprintGainer', 24, 35.00),
       ('SprintGainer', 52, 34.00),
       ('SprintGainer', 104, 33.00),
       ('AerobicMan', 24, 32.50),
       ('AerobicMan', 52, 31.50),
       ('AerobicMan', 104, 30.50),
       ('MarathonRunner', 24, 30.00),
       ('MarathonRunner', 52, 29.00),
       ('MarathonRunner', 104, 28.00),
       ('SupermanStrain', 24, 25.00),
       ('SupermanStrain', 52, 24.00),
       ('SupermanStrain', 104, 23.00);


INSERT INTO users (password, enabled, email)
VALUES ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'aron@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'bart@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'charon@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'donna@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'eric@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'frank@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'geraldine@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'mariette@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'ilona@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'janneke@api.nl');
