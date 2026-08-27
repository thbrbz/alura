package com.thbrbz.checkpointNivel1.dto;

import com.thbrbz.checkpointNivel1.entities.Sala;

public record SalaDto(
        Long id,
        String nome,
        Long capacidade,
        Boolean ativa
) {

    public SalaDto(Sala s) {
        this(s.getId(), s.getNome(), s.getCapacidade(), s.isAtiva());
    }
}
