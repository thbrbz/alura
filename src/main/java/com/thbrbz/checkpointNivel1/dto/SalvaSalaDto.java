package com.thbrbz.checkpointNivel1.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record SalvaSalaDto(@NotBlank String nome,
                           @NotNull @Positive Long capacidade,
                           @NotNull LocalDate dataInicio,
                           @NotNull LocalDate dataFim) {

    @AssertTrue(message = "A data de término deve ser posterior à data de início")
    public boolean isPeriodoValido() {
        if (dataInicio == null || dataFim == null)
            return true;

        return dataFim.isAfter(dataInicio);
    }
}
