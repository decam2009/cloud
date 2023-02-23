CREATE TABLE credential
(
    login    VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    CONSTRAINT pk_credential PRIMARY KEY (login)
);
CREATE TABLE users
(
    login VARCHAR(255) NOT NULL,
    id    BIGINT,
    name  VARCHAR(255),
    home  VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (login)
);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_LOGIN FOREIGN KEY (login) REFERENCES credential (login);
