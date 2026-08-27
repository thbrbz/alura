package com.thbrbz.checkpointNivel1.controllers;

import com.thbrbz.checkpointNivel1.dto.CriaReservaDto;
import com.thbrbz.checkpointNivel1.dto.ReservaDto;
import com.thbrbz.checkpointNivel1.exceptions.ReservaException;
import com.thbrbz.checkpointNivel1.services.ReservaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private static final Logger log = LoggerFactory.getLogger(ReservaController.class);

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaDto> criar(@RequestBody @Valid CriaReservaDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.criar(dto));
        } catch (ReservaException e) {
            log.error(e.getMessage());
            return ResponseEntity.unprocessableContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelar(@PathVariable Long id) {
        try {
            reservaService.cancelar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Reserva cancelada com sucesso!");
        } catch (ReservaException e) {
            log.error(e.getMessage());
            return ResponseEntity.unprocessableContent().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ReservaDto>> listar() {
        return ResponseEntity.ok(reservaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDto> buscar(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reservaService.buscarDto(id));
        } catch (ReservaException e) {
            log.error(e.getMessage());
            return ResponseEntity.unprocessableContent().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ReservaDto>> buscarPorUsuario(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reservaService.buscarPorUsuario(id));
        } catch (ReservaException e) {
            log.error(e.getMessage());
            return ResponseEntity.unprocessableContent().build();
        }
    }

    @GetMapping("/sala/{id}")
    public ResponseEntity<List<ReservaDto>> buscarPorSala(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reservaService.buscarPorSala(id));
        } catch (ReservaException e) {
            log.error(e.getMessage());
            return ResponseEntity.unprocessableContent().build();
        }
    }

}
