create table usuarios(
    id bigserial,
    login varchar(100) not null unique,
    senha varchar(100) not null,

    primary key(id)
);
