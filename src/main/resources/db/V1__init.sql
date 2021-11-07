CREATE TABLE  IF NOT EXISTS reader (
  id bigserial PRIMARY KEY,
  creation_date DATE  not null default CURRENT_DATE,
  last_modification_date DATE  not null default CURRENT_DATE,
  status varchar(200) DEFAULT NULL,
  first_name varchar(250) DEFAULT NULL,
  last_name varchar(250) DEFAULT NULL,
  phone varchar(250) DEFAULT NULL,
  email varchar(250) DEFAULT NULL,
  city varchar(250) DEFAULT NULL
);


