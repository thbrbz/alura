package com.thbrbz.checkpointNivel1.controllers;

import com.thbrbz.checkpointNivel1.dto.AtualizaSalaDto;
import com.thbrbz.checkpointNivel1.dto.SalaDto;
import com.thbrbz.checkpointNivel1.dto.SalvaSalaDto;
import com.thbrbz.checkpointNivel1.services.SalaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @PostMapping("/nova")
    public ResponseEntity<SalaDto> salvar(@RequestBody @Valid SalvaSalaDto dto, UriComponentsBuilder uriBuilder) {
        SalaDto sala = salaService.salvar(dto);
        URI endereco = uriBuilder.path("/salas/{id}").buildAndExpand(sala.id()).toUri();

        return ResponseEntity.created(endereco).body(sala);
    }

    @GetMapping()
    public ResponseEntity<List<SalaDto>> listar() {
        return ResponseEntity.ok(salaService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(salaService.buscarDto(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizaSalaDto dto) {
        return ResponseEntity.ok(salaService.Atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> desativar(@PathVariable Long id) {
        salaService.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
