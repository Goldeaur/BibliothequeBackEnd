CREATE TABLE IF NOT EXISTS book (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  isbn_10 varchar(50) DEFAULT NULL,
  isbn_13 varchar(50) DEFAULT NULL,
  image_link varchar(500) DEFAULT null,
  title varchar(250) DEFAULT NULL,
  author varchar(250) DEFAULT NULL,
  epoch varchar (250) DEFAULT NULL,
  nationality varchar(250) DEFAULT NULL,
  type varchar(250) DEFAULT NULL,
  sub_type varchar(250) DEFAULT NULL,
  reader_category varchar(250) DEFAULT NULL,
  comment varchar(250) DEFAULT NULL,
  description TEXT DEFAULT NULL,
  ref_bibli varchar (250) NOT NULL,
  creation_date datetime not null default now(),
  last_modification_date datetime not null default now(),
  status varchar(50) not null DEFAULT 'AVAILABLE');


CREATE TABLE IF NOT EXISTS credentials (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  login varchar(250) NOT NULL,
  password varchar(250) NOT NULL,
  phone varchar(250) NOT NULL,
  email varchar(250) NOT NULL UNIQUE,
  creation_date datetime not null default now(),
  last_modification_date datetime not null default now(),
  role varchar(50) DEFAULT 'reader' not null);

CREATE TABLE IF NOT EXISTS reader (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  first_name varchar(250) DEFAULT NULL,
  last_name varchar(250) DEFAULT NULL,
  city varchar(250) DEFAULT NULL,
  creation_date datetime not null default now(),
  last_modification_date datetime not null default now(),
  credentials_id bigint not null UNIQUE,
  status varchar(50) DEFAULT null,
  CONSTRAINT FK_reader_credentials FOREIGN KEY (credentials_id)
  REFERENCES credentials(id)  ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS loan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    creation_date datetime not null default now(),
    end_date datetime not null default now(),
    return_date datetime not null default now(),
    reader_id bigint not null,
    book_id bigint not null,
    status varchar(50) not null,
    CONSTRAINT FK_loan_reader FOREIGN KEY (reader_id)
    REFERENCES reader(id),
    CONSTRAINT FK_loan_book FOREIGN KEY (book_id)
    REFERENCES book(id));

CREATE TABLE IF NOT EXISTS reservation (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        creation_date datetime not null default now(),
        end_date datetime not null,
        return_date datetime,
        book_id bigint not null,
        reader_id bigint not null,
        status varchar(50) not null,
        CONSTRAINT FK_reservation_reader FOREIGN KEY (reader_id)
        REFERENCES reader(id),
        CONSTRAINT FK_reservation_book FOREIGN KEY (book_id)
        REFERENCES book(id));
