package com.thbrbz.checkpointNivel1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AtualizaUsuarioDto(
        @NotBlank @Size(min = 2) String nome,
        @NotBlank @Email String email,
        @NotBlank String senha
) {}
