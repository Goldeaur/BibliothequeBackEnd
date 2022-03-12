CREATE TABLE IF NOT EXISTS credentials (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  login varchar(250) NOT NULL,
  password varchar(250) NOT NULL,
  phone varchar(250) NOT NULL,
  email varchar(250) NOT NULL,
  creation_date DATE not null DEFAULT (CURRENT_DATE),
  last_modification_date DATE not null DEFAULT (CURRENT_DATE),
  role varchar(50) DEFAULT 'reader' not null);


CREATE TABLE IF NOT EXISTS reader (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  first_name varchar(250) DEFAULT NULL,
  last_name varchar(250) DEFAULT NULL,
  city varchar(250) DEFAULT NULL,
  creation_date DATE not null DEFAULT (CURRENT_DATE),
  last_modification_date DATE not null DEFAULT (CURRENT_DATE),
  credentials_id bigint not null,
  status varchar(50) DEFAULT null,
  CONSTRAINT FK_credentials FOREIGN KEY (credentials_id)
  REFERENCES credentials(id)  ON DELETE CASCADE);