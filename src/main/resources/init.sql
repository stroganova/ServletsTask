DROP TABLE IF EXISTS actors_performances CASCADE;
DROP TABLE IF EXISTS actors CASCADE;
DROP TABLE IF EXISTS performances CASCADE;
DROP TABLE IF EXISTS halls CASCADE;


CREATE TABLE actors (
    id SERIAL PRIMARY KEY,
    firstname VARCHAR(25) NOT NULL,
    lastname VARCHAR(25) NOT NULL,
    phone VARCHAR(25) NOT NULL
);

CREATE TABLE halls (
    id SERIAL PRIMARY KEY,
    hall_name VARCHAR(30) NOT NULL,
    hall_address VARCHAR(80) NOT NULL,
    hall_phone VARCHAR(25) NOT NULL
);

CREATE TABLE performances (
                              id SERIAL PRIMARY KEY,
                              name VARCHAR(30) NOT NULL,
                              description VARCHAR(80) NOT NULL,
                              hall_id INT,
                              FOREIGN KEY (hall_id) REFERENCES halls (id)
                                  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE actors_performances (
    actor_id INT NOT NULL,
    performance_id INT NOT NULL,
    PRIMARY KEY (actor_id, performance_id),
    FOREIGN KEY (actor_id) REFERENCES actors (id)
    ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (performance_id) REFERENCES performances (id)
    ON DELETE CASCADE ON UPDATE CASCADE
);