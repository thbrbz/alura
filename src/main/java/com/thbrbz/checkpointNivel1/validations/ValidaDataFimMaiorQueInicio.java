package com.thbrbz.checkpointNivel1.validations;

import com.thbrbz.checkpointNivel1.Exceptions.SalaException;

import java.time.LocalDate;

public class ValidaDataFimMaiorQueInicio implements SalaValidations{

    @Override
    public void validar(LocalDate inicio, LocalDate fim) {
        if (inicio.isAfter(fim))
            throw new SalaException("A data de término deve ser posterior à data de início, data inicio: %s / data fim: %s"
                    .formatted(inicio, fim));
    }
}
