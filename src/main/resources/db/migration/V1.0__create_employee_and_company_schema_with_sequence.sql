CREATE TABLE IF NOT EXISTS company(
    id int not null auto_increment primary key,
    company_name  varchar(255) not null
);

CREATE TABLE IF NOT EXISTS employee(
 id int not null auto_increment primary key ,
 name   varchar(255) not null,
 age    int,
 salary int,
 gender varchar(10),
 company_id int,
 FOREIGN KEY (company_id) REFERENCES Company(id)
);

CREATE OR REPLACE SEQUENCE company_seq
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;

  CREATE OR REPLACE SEQUENCE employee_seq
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;