INSERT INTO members (password, email, enabled, date_of_birth, firstname, lastname)
VALUES ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', 'member@api.nl', TRUE, '2000-01-01', 'Aron', 'Arrow'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', 'trainer@api.nl', TRUE, '2000-01-01', 'Barry', 'Bear'),
       ('$2a$10$M6Pmk1Zbmdl1o2C4B9a.belrb3Ryg4AyxjZN1sgipsJOCdRkjXMda', 'admin@api.nl', TRUE, '2000-01-01', 'Charlie', 'Charming');

INSERT INTO authorities (email, authority)
VALUES ('member@api.nl', 'ROLE_MEMBER'),
       ('trainer@api.nl', 'ROLE_MEMBER'),
       ('trainer@api.nl', 'ROLE_TRAINER'),
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

