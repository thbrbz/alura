package com.thbrbz.checkpointNivel1.controllers;

import com.thbrbz.checkpointNivel1.exceptions.SalaException;
import com.thbrbz.checkpointNivel1.dto.SalaDto;
import com.thbrbz.checkpointNivel1.dto.SalvaSalaDto;
import com.thbrbz.checkpointNivel1.services.SalaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sala")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @PostMapping("/nova")
    public ResponseEntity<String> salvar(@Valid @RequestBody SalvaSalaDto dto) {
        try {
            salaService.salvar(dto);
            return ResponseEntity.ok("Sala cadastrada com sucesso!");
        } catch (SalaException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<SalaDto>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(salaService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDto> buscar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(salaService.buscarDto(id));
        } catch (SalaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizar(@Valid @RequestBody SalaDto dto) {
        try {
            salaService.Atualizar(dto);
            return ResponseEntity.ok("Sala atualizada com sucesso!");
        } catch (SalaException e) {
            return ResponseEntity.unprocessableContent().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> desativar(@PathVariable Long id) {
        try {
            salaService.desativar(id);
            return ResponseEntity.ok().body("Sala %s desativada com sucesso!".formatted(id));
        } catch (SalaException e) {
            return ResponseEntity.unprocessableContent().body(e.getMessage());
        }
    }
}
