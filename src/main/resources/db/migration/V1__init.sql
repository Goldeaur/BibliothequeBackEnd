CREATE TABLE IF NOT EXISTS credentials (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  login varchar(250) NOT NULL,
  password varchar(250) NOT NULL,
  phone varchar(250) NOT NULL,
  email varchar(250) NOT NULL UNIQUE,
  creation_date datetime not null,
  last_modification_date datetime not null,
  role varchar(50) DEFAULT 'reader' not null);

CREATE TABLE IF NOT EXISTS reader (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  first_name varchar(250) DEFAULT NULL,
  last_name varchar(250) DEFAULT NULL,
  city varchar(250) DEFAULT NULL,
  creation_date datetime not null,
  last_modification_date datetime not null,
  credentials_id bigint not null UNIQUE,
  status varchar(50) DEFAULT null,
  CONSTRAINT FK_credentials FOREIGN KEY (credentials_id)
  REFERENCES credentials(id)  ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS book (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  isbn BIGINT DEFAULT NULL,
  title varchar(250) DEFAULT NULL,
  author varchar(250) DEFAULT NULL,
  epoch varchar (250) DEFAULT NULL,
  nationality varchar(250) DEFAULT NULL,
  type varchar(250) DEFAULT NULL,
  sub_type varchar(250) DEFAULT NULL,
  reader_category varchar(250) DEFAULT NULL,
  comment varchar(250) DEFAULT NULL,
  ref_bibli varchar (250) NOT NULL,
  creation_date datetime not null,
  last_modification_date datetime not null,
  status varchar(50) DEFAULT null
  );

CREATE TABLE IF NOT EXISTS loan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_date datetime not null,
    return_date datetime not null,
    reader_id bigint not null,
    book_id bigint not null,
    status varchar(50) not null,
    CONSTRAINT FK_reader FOREIGN KEY (reader_id)
    REFERENCES reader(id),
    CONSTRAINT FK_book FOREIGN KEY (book_id)
    REFERENCES book(id)
    );