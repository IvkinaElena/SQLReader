CREATE TABLE CUSTOMERS(
                          ID   INT              NOT NULL,
                          NAME VARCHAR (20)     NOT NULL,
                          AGE  INT              NOT NULL,
                          ADDRESS  CHAR (25) ,
                          SALARY   DECIMAL (18, 2),
                          PRIMARY KEY (ID)
);
CREATE TABLE films (
                       code        char(5) CONSTRAINT PRIMARY KEY,
                       title       varchar(40) NOT NULL,
                       did         integer NOT NULL,
                       date_prod   date,
                       kind        varchar(10),
                       len         interval hour to minute
);
CREATE TABLE distributors (
                              did    integer PRIMARY KEY DEFAULT nextval('serial'),
                              name   varchar(40) NOT NULL CHECK (name <> '')
);
CREATE TABLE cinemas (
                         id serial,
                         name text,
                         location text
) TABLESPACE diskvol1;
