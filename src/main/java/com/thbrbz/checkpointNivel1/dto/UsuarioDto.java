package com.thbrbz.checkpointNivel1.dto;

import com.thbrbz.checkpointNivel1.entities.Usuario;

public record UsuarioDto(
        Long id,
        String nome,
        String email,
        String senha) {

    public UsuarioDto(Usuario u) {
        this(u.getId(), u.getNome(), u.getEmail(), u.getSenha());
    }
}
