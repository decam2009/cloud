CREATE TABLE users
(
    login VARCHAR(255) NOT NULL,
    id    BIGINT,
    name  VARCHAR(255),
    home  VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (login)
);
CREATE TABLE credential
(
    login    VARCHAR(255) NOT NULL,
    id       BIGINT       NOT NULL,
    password VARCHAR(255),
    CONSTRAINT pk_credential PRIMARY KEY (login)
);

ALTER TABLE credential
    ADD CONSTRAINT FK_CREDENTIAL_ON_LOGIN FOREIGN KEY (login) REFERENCES users (login);

CREATE TABLE storage
(
    id         BIGINT NOT NULL,
    user_login VARCHAR(255),
    file_name  VARCHAR(255),
    type       VARCHAR(255),
    file_hash  VARCHAR(255),
    data       OID,
    CONSTRAINT pk_storage PRIMARY KEY (id)
);

ALTER TABLE storage
    ADD CONSTRAINT FK_STORAGE_ON_USER_LOGIN FOREIGN KEY (user_login) REFERENCES users (login);