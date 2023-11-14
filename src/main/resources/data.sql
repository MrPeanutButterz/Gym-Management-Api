INSERT INTO admins (password, enabled, date_of_birth, email, firstname, lastname)
VALUES ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'admin@api.nl', 'Charlie', 'Charming');

INSERT INTO trainers (password, enabled, date_of_birth, email, firstname, lastname, hourly_rate)
VALUES ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'andy@api.nl', 'Andy', 'Ashnick', 55),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'barry@api.nl', 'Barry', 'Barten', 65.50),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'charlene@api.nl', 'Charlene', 'Cornelis', 85);

INSERT INTO members (password, enabled, date_of_birth, email, firstname, lastname)
VALUES ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'aron@api.nl', 'Aron', 'Arrow'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'bart@api.nl', 'Bart', 'Bonus'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'charon@api.nl', 'Charon', 'Cals'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'donna@api.nl', 'Donna', 'Dolger'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'eric@api.nl', 'Eric', 'El Dona'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'frank@api.nl', 'Frank', 'Flower'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'geraldine@api.nl', 'Geraldine', 'Gegeven'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'hariette@api.nl', 'Hariette', 'Hartswarm'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'ilona@api.nl', 'Ilona', 'Iets'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '1990-01-01', 'janneke@api.nl', 'Janneke', 'Jasweit');

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
       ('chad@api.nl', 'ROLE_MEMBER'),
       ('chad@api.nl', 'ROLE_TRAINER'),
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

