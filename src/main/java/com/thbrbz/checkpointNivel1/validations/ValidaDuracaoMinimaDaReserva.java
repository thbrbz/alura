package com.thbrbz.checkpointNivel1.validations;

import com.thbrbz.checkpointNivel1.dto.CriaReservaDto;
import com.thbrbz.checkpointNivel1.exceptions.ReservaException;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ValidaDuracaoMinimaDaReserva implements ReservaValidations {

    @Override
    public void validar(CriaReservaDto dto) {
        Duration d = Duration.between(dto.dataInicio(), dto.dataFim());

        if (d.toHours() < 2)
            throw new ReservaException("Duração da reserva não pode ser menor que duas horas!");
    }
}
