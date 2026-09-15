ALTER TABLE "consultas" DROP CONSTRAINT fk_consultas_paciente_id;

ALTER TABLE "pacientes"
    ALTER COLUMN id TYPE BIGINT,
    ALTER COLUMN id DROP DEFAULT;

DROP SEQUENCE IF EXISTS pacientes_id_seq;

ALTER TABLE consultas
    ADD CONSTRAINT fk_consultas_paciente_id
        FOREIGN KEY (paciente_id) REFERENCES pacientes(id);