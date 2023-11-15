INSERT INTO authorities (email, authority)
VALUES ('aron@api.nl', 'ROLE_MEMBER'),
       ('bart@api.nl', 'ROLE_MEMBER'),
       ('charon@api.nl', 'ROLE_MEMBER'),
       ('donna@api.nl', 'ROLE_MEMBER'),
       ('eric@api.nl', 'ROLE_MEMBER'),
       ('frank@api.nl', 'ROLE_MEMBER'),
       ('geraldine@api.nl', 'ROLE_MEMBER'),
       ('hariette@api.nl', 'ROLE_MEMBER'),
       ('ilona@api.nl', 'ROLE_MEMBER'),
       ('janneke@api.nl', 'ROLE_MEMBER'),
       ('andy@api.nl', 'ROLE_MEMBER'),
       ('andy@api.nl', 'ROLE_TRAINER'),
       ('barry@api.nl', 'ROLE_MEMBER'),
       ('barry@api.nl', 'ROLE_TRAINER'),
       ('charlene@api.nl', 'ROLE_MEMBER'),
       ('charlene@api.nl', 'ROLE_TRAINER'),
       ('admin@api.nl', 'ROLE_MEMBER'),
       ('admin@api.nl', 'ROLE_TRAINER'),
       ('admin@api.nl', 'ROLE_ADMIN');

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

INSERT INTO admins (password, enabled, date_of_birth, email, firstname, lastname)
VALUES ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'admin@api.nl', 'Admin', 'Api');

INSERT INTO trainers (password, enabled, date_of_birth, email, firstname, lastname, hourly_rate)
VALUES ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'andy@api.nl', 'Andy', 'Ashnick', 55),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'barry@api.nl', 'Barry', 'Barten', 65.50),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'charlene@api.nl', 'Charlene', 'Cornelis', 85);

INSERT INTO members (password, enabled, date_of_birth, email, firstname, lastname, membership_id, trainer_email)
VALUES ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'aron@api.nl', 'Aron', 'Arrow', 2, 'charlene@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'bart@api.nl', 'Bart', 'Bonus', 4, 'andy@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'charon@api.nl', 'Charon', 'Cals', 4, 'barry@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'donna@api.nl', 'Donna', 'Dolger', 11, 'charlene@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'eric@api.nl', 'Eric', 'Elenar', 6, 'charlene@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'frank@api.nl', 'Frank', 'Flower', 8, 'barry@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'geraldine@api.nl', 'Geraldine', 'Gegeven', 7, 'charlene@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'hariette@api.nl', 'Hariette', 'Hartswarm', 11, 'charlene@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'ilona@api.nl', 'Ilona', 'Iets', 9, 'charlene@api.nl'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'janneke@api.nl', 'Janneke', 'Jasweit', 11, 'charlene@api.nl');

INSERT INTO goals (id, current_body_weight, target_body_weight, target_calorie_intake, end_date, start_date, description, member_email)
VALUES (1, 103, 95, 2400, '2024-03-04', '2023-11-09', 'Needs to lose weight', 'aron@api.nl'),
       (2, 76, 72, 2100, '2024-03-04', '2023-11-09', 'Needs to lose weight', 'bart@api.nl'),
       (11, 76, 72, 2100, '2024-03-04', '2024-07-25', 'Build muscle strength', 'bart@api.nl'),
       (3, 82, 75, 2200, '2024-03-04', '2023-11-09', 'Needs to lose weight', 'charon@api.nl'),
       (4, 65, 70, 1900, '2024-03-04', '2023-11-09', 'Needs to gain weight', 'donna@api.nl'),
       (5, 135, 100, 2400, '2024-03-04', '2023-11-09', 'Needs to lose weight', 'eric@api.nl'),
       (6, 101, 95, 2350, '2024-03-04', '2023-11-09', 'Needs to lose weight', 'frank@api.nl'),
       (7, 84, 80, 2200, '2024-03-04', '2023-11-09', 'Needs to lose weight', 'geraldine@api.nl'),
       (8, 75, 75, 2000, '2024-03-04', '2023-11-09', 'Wants to get fitter', 'hariette@api.nl'),
       (9, 62, 65, 1850, '2024-03-04', '2023-11-09', 'Needs to gain weight', 'ilona@api.nl'),
       (10, 90, 80, 2250, '2024-03-04', '2023-11-09', 'Needs to lose weight', 'janneke@api.nl');