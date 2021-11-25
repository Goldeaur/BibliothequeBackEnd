CREATE TABLE IF NOT EXISTS reader (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  first_name varchar(250) DEFAULT NULL,
  last_name varchar(250) DEFAULT NULL,
  phone varchar(250) DEFAULT NULL,
  email varchar(250) DEFAULT NULL,
  city varchar(250) DEFAULT NULL,
  creation_date BIGINT not null,
  last_modification_date BIGINT not null,
  status varchar(50) DEFAULT NULl);

