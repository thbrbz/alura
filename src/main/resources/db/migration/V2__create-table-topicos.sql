CREATE TYPE status_topicos AS ENUM ('NAO_RESPONDIDO', 'RESPONDIDO', 'RESOLVIDO');

CREATE TABLE topicos (
    id BIGINT NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    mensagem TEXT NOT NULL,
    autor VARCHAR(255) NOT NULL,
    categoria categorias NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    status status_topicos NOT NULL,
    aberto BOOLEAN NOT NULL,
    quantidade_respostas INT NOT NULL,
    curso_id BIGINT,

    CONSTRAINT fk_curso FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE SET NULL,
    primary key(id)
);