create table users
(
    id serial not null
        constraint users_pkey
            primary key,
    nickname varchar(64),
    password varchar(64),
    avatarid integer
);

alter table users owner to postgres;

create table tasks
(
    id serial not null
        constraint tasks_pkey
            primary key,
    title varchar(64),
    description varchar(2048)
);

alter table tasks owner to postgres;

create table packages
(
    id serial not null
        constraint packages_pkey
            primary key,
    user_id integer,
    task_id integer,
    attempttime varchar(32),
    lang varchar(32),
    code varchar(8192),
    result varchar(128),
    message varchar(1024),
    score integer
);

alter table packages owner to postgres;

create table file_info
(
    id serial not null
        constraint file_info_pkey
            primary key,
    original_file_name varchar(100),
    storage_file_name varchar(100) not null,
    size bigint not null,
    type varchar(100)
);

alter table file_info owner to postgres;

