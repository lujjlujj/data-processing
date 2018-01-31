INSERT INTO users(username,password,enabled)
VALUES ('Terry','password', true);
INSERT INTO users(username,password,enabled)
VALUES ('naveen','naveen', true);
INSERT INTO user_roles (username, role)
VALUES ('Terry', 'ROLE_USER');
INSERT INTO user_roles (username, role)
VALUES ('Terry', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role)
VALUES ('naveen', 'ROLE_USER');