INSERT INTO members (password, email, enabled, date_of_birth, firstname, lastname)
VALUES ('$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'member@test.nl', TRUE, '2000-01-01', 'Piet', 'Pont'),
       ('$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'trainer@test.nl', TRUE, '2000-01-01', 'Barry', 'Bear'),
       ('$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'admin@test.nl', TRUE, '2000-01-01', 'Charlie', 'Eysbroek');

INSERT INTO authorities (email, authority)
VALUES ('member@test.nl', 'ROLE_MEMBER'),
       ('trainer@test.nl', 'ROLE_MEMBER'),
       ('trainer@test.nl', 'ROLE_TRAINER'),
       ('admin@test.nl', 'ROLE_MEMBER'),
       ('admin@test.nl', 'ROLE_TRAINER'),
       ('admin@test.nl', 'ROLE_ADMIN');

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

