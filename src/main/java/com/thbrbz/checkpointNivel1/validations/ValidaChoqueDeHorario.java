package com.thbrbz.checkpointNivel1.validations;

import com.thbrbz.checkpointNivel1.dto.CriaReservaDto;
import com.thbrbz.checkpointNivel1.enums.ReservaStatus;
import com.thbrbz.checkpointNivel1.exceptions.ReservaException;
import com.thbrbz.checkpointNivel1.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaChoqueDeHorario implements ReservaValidations{

    @Autowired
    ReservaRepository reservaRepository;

    @Override
    public void validar(CriaReservaDto dto) {
        boolean existeConflito = reservaRepository.existeConflitoDeHorario(dto.salaId(), ReservaStatus.ATIVA, dto.dataInicio(), dto.dataFim());

        if (existeConflito)
            throw new ReservaException("Já existe uma reserva ativa para essa sala no horário informado!");
    }
}
