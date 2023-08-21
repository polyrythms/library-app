CREATE table Book(
                     id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                     title varchar(100) NOT NULL,
                     author varchar(100) NOT NULL,
                     year int check (year > 0),
                     person_id int REFERENCES Person(id) ON DELETE SET NULL,
                     signed_at TIMESTAMP
);
CREATE TABLE Person(
                       id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                       name varchar(100) NOT NULL,
                       year_of_birth int
);