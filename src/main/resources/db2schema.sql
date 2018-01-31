CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(45) NOT NULL ,
  enabled VARCHAR(1) NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));
CREATE TABLE user_roles (
  user_role_id integer not null,
  username varchar(45) not null,
  role varchar(45) not null,
  CONSTRAINT user_roles_key PRIMARY KEY (user_role_id),
  CONSTRAINT uni_username_role UNIQUE (role, username)
)

CREATE SEQUENCE seq_user_roles
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  CACHE 10

INSERT INTO users(username,password,enabled)
VALUES ('Terry','password', '1');
INSERT INTO users(username,password,enabled)
VALUES ('naveen','naveen', '1');
INSERT INTO user_roles (user_role_id, username, role)
VALUES (seq_user_roles.nextval, 'Terry', 'ROLE_USER');
INSERT INTO user_roles (user_role_id, username, role)
VALUES (seq_user_roles.nextval, 'Terry', 'ROLE_ADMIN');
INSERT INTO user_roles (user_role_id, username, role)
VALUES (seq_user_roles.nextval, 'naveen', 'ROLE_USER');