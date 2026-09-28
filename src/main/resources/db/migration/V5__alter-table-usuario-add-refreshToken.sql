ALTER TABLE usuarios
    ADD COLUMN refresh_token VARCHAR(64) UNIQUE,
    ADD COLUMN expiracao_refresh_token TIMESTAMP;