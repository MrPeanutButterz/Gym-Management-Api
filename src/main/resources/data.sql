INSERT INTO members (password, enabled, date_of_birth, email, firstname, lastname)
VALUES ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '2000-01-01', 'Aron@api.nl', 'Aron', 'Arrow'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '2000-01-01', 'Bart@api.nl', 'Bart', 'Bonus'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '2000-01-01', 'Charon@api.nl', 'Charon', 'Cals'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '2000-01-01', 'Donna@api.nl', 'Donna', 'Dolger'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '2000-01-01', 'Eric@api.nl', 'Eric', 'El Dona'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '2000-01-01', 'Frank@api.nl', 'Frank', 'Flower'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '2000-01-01', 'Geraldine@api.nl', 'Geraldine', 'Gegeven'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '2000-01-01', 'Hariette@api.nl', 'Hariette', 'Hartswarm'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '2000-01-01', 'Ilona@api.nl', 'Ilona', 'Iets'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '2000-01-01', 'Janneke@api.nl', 'Janneke', 'Jasweit');

INSERT INTO trainers (password, enabled, date_of_birth, email, firstname, lastname, hourly_rate)
VALUES ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '2000-01-01', 'Barry@api.nl', 'Barry', 'Bear', 50);

INSERT INTO admins (password, enabled, date_of_birth, email, firstname, lastname)
VALUES ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', true, '2000-01-01', 'admin@api.nl', 'Charlie', 'Charming');

INSERT INTO authorities (email, authority)
VALUES ('Aron@api.nl', 'ROLE_MEMBER'),
       ('Bart@api.nl', 'ROLE_MEMBER'),
       ('Charon@api.nl', 'ROLE_MEMBER'),
       ('Donna@api.nl', 'ROLE_MEMBER'),
       ('Eric@api.nl', 'ROLE_MEMBER'),
       ('Frank@api.nl', 'ROLE_MEMBER'),
       ('Geraldine@api.nl', 'ROLE_MEMBER'),
       ('Hariette@api.nl', 'ROLE_MEMBER'),
       ('Ilona@api.nl', 'ROLE_MEMBER'),
       ('Janneke@api.nl', 'ROLE_MEMBER'),
       ('Barry@api.nl', 'ROLE_MEMBER'),
       ('Barry@api.nl', 'ROLE_TRAINER'),
       ('admin@api.nl', 'ROLE_MEMBER'),
       ('admin@api.nl', 'ROLE_TRAINER'),
       ('admin@api.nl', 'ROLE_ADMIN');

insert into memberships (name, contract_length_in_week, price_Per_Month)
values ('No membership', 0, 00.00),
       ('SprintGainer', 24, 35.00),
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

