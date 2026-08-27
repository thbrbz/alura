package com.thbrbz.checkpointNivel1.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CriaReservaDto(
        @NotNull LocalDateTime dataInicio,
        @NotNull LocalDateTime dataFim,
        @NotNull Long salaId,
        @NotNull @NotEmpty List<Long> usuariosIds
) {}
