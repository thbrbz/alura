package com.thbrbz.checkpointNivel1.dto;

import com.thbrbz.checkpointNivel1.entities.Reserva;
import com.thbrbz.checkpointNivel1.entities.Sala;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record SalaDto(@NotNull Long id,
                      Reserva reserva,
                      @NotBlank String nome,
                      @NotNull @Positive Long capacidade,
                      @NotNull LocalDate dataInicio,
                      @NotNull LocalDate dataFim,
                      @NotNull Boolean ativa) {

    public SalaDto(Sala s) {
        this(s.getId(), s.getReserva(), s.getNome(), s.getCapacidade(), s.getDataInicio(), s.getDataFim(), s.isAtiva());
    }
}
