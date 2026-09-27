CREATE TABLE usuarios(
     id BIGSERIAL NOT NULL,
     email VARCHAR(100) NOT NULL UNIQUE,
     senha VARCHAR(100) NOT NULL,
     nome VARCHAR(100) NOT NULL,
     username VARCHAR(100) NOT NULL UNIQUE,
     mini_biografia VARCHAR(30),
     biografia TEXT,

    primary key (id)
);