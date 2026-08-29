package com.thbrbz.checkpointNivel1.validations;

import com.thbrbz.checkpointNivel1.dto.CriaReservaDto;
import com.thbrbz.checkpointNivel1.entities.Sala;
import com.thbrbz.checkpointNivel1.exceptions.ReservaException;
import com.thbrbz.checkpointNivel1.services.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaSeSalaInativa implements ReservaValidations{

    @Autowired
    private SalaService salaService;

    @Override
    public void validar(CriaReservaDto dto) {
        Sala sala = salaService.buscar(dto.salaId());

        if (!sala.isAtiva())
            throw new ReservaException("Sala informada não pode ser usada por estar inativa!");
    }
}
