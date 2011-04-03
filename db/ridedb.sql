CREATE TABLE users (
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  user_type SMALLINT NOT NULL DEFAULT 0,
  login varchar(25) NOT NULL,
  salutation varchar(5) DEFAULT NULL,
  name varchar(120) NOT NULL,
  birthdate date NOT NULL,
  password varchar(32) NOT NULL,
  email varchar(300) NOT NULL,
  last_login timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  referal varchar(255) DEFAULT NULL,
  status SMALLINT NOT NULL,
  CONSTRAINT userPK PRIMARY KEY (id)
);

CREATE TABLE cities (
  id smallint NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  countryid smallint DEFAULT 1,
  city varchar(255) NOT NULL,
  status smallint DEFAULT 1,
  CONSTRAINT cityPK PRIMARY KEY (id)
);




INSERT INTO cities (countryid, city, status) VALUES
(1, 'Santo Domingo', 1),
(1, 'Santo Domingo Este', 0),
(1, 'Santo Domingo Norte', 0),
(1, 'Santo Domingo Oeste', 0),
(1, 'San Pedro de Macoris', 1),
(1, 'San Cristobal', 1),
(1, 'Monte Plata', 1);




CREATE TABLE contacts (
  id integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  country varchar(255) DEFAULT '1',
  city varchar(255) DEFAULT '1',
  sector varchar(255) NOT NULL,
  street varchar(100) NOT NULL,
  number varchar(10) NOT NULL,
  apto varchar(255) DEFAULT NULL,
  telephone varchar(15) NOT NULL,
  cell varchar(15) DEFAULT NULL,
  bbpin varchar(8) DEFAULT NULL,
  email varchar(255) not null,
  CONSTRAINT contactPK PRIMARY KEY (id)
);


CREATE TABLE mail_templates (
  id smallint NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  name varchar(20) NOT NULL,
  template clob NOT NULL,
  subject varchar(255) NOT NULL,
  CONSTRAINT templatePK PRIMARY KEY (name)
);

CREATE TABLE countries (
  id smallint NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  name varchar(255) NOT NULL,
  status smallint DEFAULT 1,
  CONSTRAINT countryPK PRIMARY KEY (name)
);

INSERT INTO countries (name) VALUES
('República Dominicana');



CREATE TABLE usercontactmap (
  userid integer NOT NULL,
  contactid smallint NOT NULL,
   CONSTRAINT usercontactmapPK PRIMARY KEY (userid,contactid)
)

CREATE TABLE user_session (
  username varchar(30) NOT NULL,
  serie varchar(100) NOT NULL,
  token varchar(150) NOT NULL,
  CONSTRAINT userSessionPK PRIMARY KEY (username)
)

CREATE TABLE user_types (
  user_type smallint NOT NULL,
  description varchar(25) NOT NULL,
  admin smallint NOT NULL DEFAULT 0,
  status smallint NOT NULL DEFAULT 0,
  CONSTRAINT userTypePK PRIMARY KEY (user_type)
)



INSERT INTO user_types (user_type, descripton, admin, status) VALUES
(0, 'Pasajero', 0, 1),
(1, 'Conductor', 0, 1),
(2, 'Admin', 1, 1);



CREATE TABLE user_confirmations (
  confirmationid bigint NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 

1, INCREMENT BY 1),
  userid integer NOT NULL,
  email varchar(300) NOT NULL,
  confirmation_code varchar(45) NOT NULL,
  generation_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  confirmation_date timestamp DEFAULT NULL,
  confirmed smallint NOT NULL DEFAULT 0,
  CONSTRAINT userConfirmationsPK PRIMARY KEY (confirmationid)
) 