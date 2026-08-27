package com.thbrbz.checkpointNivel1.validations;

import com.thbrbz.checkpointNivel1.dto.CriaReservaDto;
import com.thbrbz.checkpointNivel1.exceptions.ReservaException;
import org.springframework.stereotype.Component;

@Component
public class ValidaDataFimMaiorQueInicio implements ReservaValidations {

    @Override
    public void validar(CriaReservaDto dto) {
        if (!dto.dataFim().isAfter(dto.dataInicio()))
            throw new ReservaException("A data de término deve ser posterior à data de início, data inicio: %s / data fim: %s"
                    .formatted(dto.dataInicio(), dto.dataFim()));
    }
}
