package com.thbrbz.checkpointNivel1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SalvaUsuarioDto(
        @NotBlank @Size(min = 3) String nome,
        @NotBlank @Email String email,
        @NotBlank String senha
) {}
