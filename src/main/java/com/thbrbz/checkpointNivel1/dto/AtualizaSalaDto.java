package com.thbrbz.checkpointNivel1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AtualizaSalaDto(
        @NotNull Long id,
        @NotBlank @Size(min = 2) String nome,
        @NotNull @Positive Long capacidade
) {}
