INSERT INTO users (password, email, enabled, date_of_birth, firstname, lastname)
VALUES ('$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'member@test.nl', TRUE, '2000-01-01', 'Piet', 'Pont'),
       ('$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'admin@test.nl', TRUE, '2000-01-01', 'Charlie', 'Eysbroek');

INSERT INTO authorities (email, authority)
VALUES ('member@test.nl', 'ROLE_USER'),
       ('admin@test.nl', 'ROLE_USER'),
       ('admin@test.nl', 'ROLE_ADMIN');