package com.thbrbz.checkpointNivel1.validations;

import java.time.LocalDate;

public interface SalaValidations {

    default void validar(LocalDate inicio, LocalDate fim) {}
}
