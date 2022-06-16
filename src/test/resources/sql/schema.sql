DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS hobbies;
DROP TABLE IF EXISTS communities;

CREATE TABLE IF NOT EXISTS communities
(
    com_id integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name character varying(100) NOT NULL UNIQUE,
    category_id integer NOT NULL
);


CREATE TABLE IF NOT EXISTS hobbies
(
    hobby_id integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name character varying(50)
);


CREATE TABLE IF NOT EXISTS categories
(
    category_id integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name character varying(50) NOT NULL,
    hobby_id integer NOT NULL,
    FOREIGN KEY (hobby_id) REFERENCES hobbies (hobby_id)
);
    