
create table users
(
    user_id     serial
        constraint users_pk
            primary key,
    name        varchar not null,
    password    varchar not null,
    role        varchar not null,
    home_folder varchar not null
);

alter table users
    owner to postgres;

