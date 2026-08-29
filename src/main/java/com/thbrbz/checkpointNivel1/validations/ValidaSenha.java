package com.thbrbz.checkpointNivel1.validations;

import com.thbrbz.checkpointNivel1.exceptions.UsuarioException;
import org.springframework.stereotype.Component;

@Component
public class ValidaSenha {

    public void validar(String senha) {
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$";

        if (senha == null || !senha.matches(regex))
            throw new UsuarioException("A senha deve conter no mínimo 8 caracteres, incluindo letras maiúsculas, minúsculas, números e caracteres especiais.");
    }
}
