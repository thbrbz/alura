package com.thbrbz.checkpointNivel1.validations;

import com.thbrbz.checkpointNivel1.dto.CriaReservaDto;

public interface ReservaValidations {

    default void validar(CriaReservaDto dto) {}
}
