ALTER TABLE cursos
    ALTER COLUMN categoria TYPE VARCHAR(50)
        USING categoria::text;

ALTER TABLE topicos
    ALTER COLUMN categoria TYPE VARCHAR(50)
        USING categoria::text;

ALTER TABLE topicos
    ALTER COLUMN status TYPE VARCHAR(50)
        USING status::text;