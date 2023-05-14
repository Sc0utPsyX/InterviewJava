CREATE SCHEMA cinema;
CREATE TABLE cinema.movie (
                              id int8 NOT NULL,
                              "name" varchar NOT NULL,
                              duration int2 NOT NULL,
                              CONSTRAINT movie_pk PRIMARY KEY (id),
                              CONSTRAINT "name" UNIQUE NULLS NOT DISTINCT (name)
);
CREATE TABLE cinema.session (
                                  id int8 NOT NULL,
                                  movie_id int8 NOT NULL,
                                  start_time time NOT NULL,
                                  price int8 NOT NULL,
                                  CONSTRAINT session_pkey PRIMARY KEY (id)
);
ALTER TABLE cinema.session ADD CONSTRAINT movie_id FOREIGN KEY (movie_id) REFERENCES cinema.movie(id) ON DELETE CASCADE ON UPDATE CASCADE;
CREATE TABLE cinema.tickets (
                                id int8 NOT NULL,
                                session_id int8 NOT NULL,
                                CONSTRAINT tickets_pkey PRIMARY KEY (id)
);
ALTER TABLE cinema.tickets ADD CONSTRAINT session_fk FOREIGN KEY (session_id) REFERENCES cinema.session(id);
INSERT INTO cinema.movie (id,"name",duration) VALUES
                                                  (5,'Movie5',60),
                                                  (4,'Movie4',120),
                                                  (3,'Movie3',90),
                                                  (2,'Movie2',120),
                                                  (1,'Movie1',30);
INSERT INTO cinema.session (id,movie_id,start_time,price) VALUES
                                                                (4,2,'14:00:00',140),
                                                                (1,1,'08:00:00',100),
                                                                (7,4,'20:00:00',170),
                                                                (8,5,'20:30:00',180),
                                                                (5,2,'16:00:00',150),
                                                                (3,2,'12:00:00',130),
                                                                (6,2,'18:00:00',160),
                                                                (2,5,'10:00:00',120);
INSERT INTO cinema.tickets (id,session_id) VALUES
                                               (7,3),
                                               (6,2),
                                               (5,1),
                                               (4,1),
                                               (3,2),
                                               (2,2),
                                               (1,2),
                                               (13,8),
                                               (12,8),
                                               (8,5);
