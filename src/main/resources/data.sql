insert into contact_information (id, firstname, lastname, dob)
values (1, 'Aron', 'Api', '2000-01-01'),
       (2, 'Bart', 'Api', '2001-01-01'),
       (3, 'Charon', 'Api', '2001-01-01'),
       (4, 'Donna', 'Api', '2001-01-01'),
       (5, 'Eric', 'Api', '2001-01-01'),
       (6, 'Frank', 'Api', '2001-01-01'),
       (7, 'Geraldine', 'Api', '2001-01-01'),
       (8, 'Mariette', 'Api', '2001-01-01'),
       (9, 'ilona', 'Api', '2001-01-01'),
       (10, 'Janneke', 'Api', '2001-10-10');


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


INSERT INTO users (password, enabled, email, contract_information_id)
VALUES ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'aron@api.nl', 1),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'bart@api.nl', 2),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'charon@api.nl', 3),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'donna@api.nl', 4),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'eric@api.nl', 5),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'frank@api.nl', 6),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'geraldine@api.nl', 7),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'mariette@api.nl', 8),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'ilona@api.nl', 9),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, 'janneke@api.nl', 10);

