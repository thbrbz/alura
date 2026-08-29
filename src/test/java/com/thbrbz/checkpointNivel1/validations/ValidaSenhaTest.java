package com.thbrbz.checkpointNivel1.validations;

import com.thbrbz.checkpointNivel1.exceptions.UsuarioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidaSenhaTest {
    private final ValidaSenha validaSenha = new ValidaSenha();

    @Test
    void deveAceitarSenhaValida() {
        assertDoesNotThrow(() -> validaSenha.validar("Senha@123"));
    }

    @Test
    void deveRejeitarSenhaSemMaiuscula() {
        UsuarioException exception = assertThrows(UsuarioException.class, () -> validaSenha.validar("senha@123"));
        assertTrue(exception.getMessage().contains("8 caracteres"));
    }

    @Test
    void deveRejeitarSenhaSemNumero() {
        UsuarioException exception = assertThrows(UsuarioException.class, () -> validaSenha.validar("Senha@abc"));
        assertTrue(exception.getMessage().contains("8 caracteres"));
    }

    @Test
    void deveRejeitarSenhaNull() {
        assertThrows(UsuarioException.class, () -> validaSenha.validar(null));
    }
}
