CREATE TYPE categorias AS ENUM ('PROGRAMACAO', 'IA', 'FRONTEND', 'DADOS', 'INOVACAO', 'MARKETING', 'DESIGN');

CREATE TABLE cursos (
    id BIGINT not null,
    nome VARCHAR(255) NOT NULL UNIQUE,
    categoria categorias NOT NULL,

    primary key(id)
);