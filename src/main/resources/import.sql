CREATE TABLE student
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255),
    mark INTEGER,
    CONSTRAINT pk_student PRIMARY KEY (id)
);