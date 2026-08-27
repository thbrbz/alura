package com.thbrbz.checkpointNivel1.dto;

import com.thbrbz.checkpointNivel1.entities.Reserva;
import com.thbrbz.checkpointNivel1.entities.Sala;
import com.thbrbz.checkpointNivel1.entities.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public record ReservaDto(
        Long id,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        Sala sala,
        List<Usuario> usuarios
) {

    public ReservaDto(Reserva r) {
        this(r.getId(), r.getDataInicio(), r.getDataFim(), r.getSala(), r.getUsuarios());
    }
}
