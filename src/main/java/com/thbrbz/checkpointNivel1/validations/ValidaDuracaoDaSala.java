package com.thbrbz.checkpointNivel1.validations;

import com.thbrbz.checkpointNivel1.Exceptions.SalaException;

import java.time.LocalDate;
import java.time.Period;

public class ValidaDuracaoDaSala implements SalaValidations {

    @Override
    public void validar(LocalDate inicio, LocalDate fim) {
        Period d = Period.between(inicio, fim);

        if (d.getDays() < 2)
            throw new SalaException("Duração da sala não pode ser menor que dois dias!");
    }
}
