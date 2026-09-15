ALTER TABLE "consultas" DROP CONSTRAINT fk_consultas_medico_id;

ALTER TABLE "medicos"
    ALTER COLUMN id TYPE BIGINT,
    ALTER COLUMN id DROP DEFAULT;

DROP SEQUENCE IF EXISTS medicos_id_seq;

ALTER TABLE consultas
    ADD CONSTRAINT fk_consultas_medico_id
        FOREIGN KEY (medico_id) REFERENCES medicos(id);