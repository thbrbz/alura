CREATE TYPE perfil_usuario AS ENUM ('ATENDENTE', 'MEDICO', 'PACIENTE');

ALTER TABLE usuarios
    ADD COLUMN perfil perfil_usuario NOT NULL default 'PACIENTE';
