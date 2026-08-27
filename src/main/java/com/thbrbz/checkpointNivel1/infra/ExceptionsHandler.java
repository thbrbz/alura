package com.thbrbz.checkpointNivel1.infra;

import com.thbrbz.checkpointNivel1.exceptions.ReservaException;
import com.thbrbz.checkpointNivel1.exceptions.SalaException;
import com.thbrbz.checkpointNivel1.exceptions.UsuarioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    private static final Logger log = LoggerFactory.getLogger(ExceptionsHandler.class);

    private ResponseEntity<String> handleException(RuntimeException e) {
        log.error(e.getMessage());
        return ResponseEntity.unprocessableContent().body(e.getMessage());
    }

    @ExceptionHandler(ReservaException.class)
    public ResponseEntity<String> handleReservaException(ReservaException re) {
        return this.handleException(re);
    }

    @ExceptionHandler(SalaException.class)
    public ResponseEntity<String> handleSalaException(SalaException se) {
        return this.handleException(se);
    }

    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<String> handleUsuarioException(UsuarioException ue) {
        return this.handleException(ue);
    }
}
