CREATE TABLE IF NOT EXISTS reader (
  id INT AUTO_INCREMENT PRIMARY KEY,
  creation_date DATE not null,
  last_modification_date DATE not null,
  status varchar(200) DEFAULT NULL,
  first_name varchar(250) DEFAULT NULL,
  last_name varchar(250) DEFAULT NULL,
  phone varchar(250) DEFAULT NULL,
  email varchar(250) DEFAULT NULL,
  city varchar(250) DEFAULT NULL);