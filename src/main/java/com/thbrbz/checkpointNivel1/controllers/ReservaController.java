package com.thbrbz.checkpointNivel1.controllers;

import com.thbrbz.checkpointNivel1.dto.CriaReservaDto;
import com.thbrbz.checkpointNivel1.dto.ReservaDto;
import com.thbrbz.checkpointNivel1.services.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaDto> criar(@RequestBody @Valid CriaReservaDto dto, UriComponentsBuilder uriBuilder) {
        ReservaDto reserva = reservaService.criar(dto);
        URI endereco = uriBuilder.path("/reservas/{id}").buildAndExpand(reserva.id()).toUri();

        return ResponseEntity.created(endereco).body(reserva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelar(@PathVariable Long id) {
        reservaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservaDto>> listar() {
        return ResponseEntity.ok(reservaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarDto(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ReservaDto>> buscarPorUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarPorUsuario(id));
    }

    @GetMapping("/sala/{id}")
    public ResponseEntity<List<ReservaDto>> buscarPorSala(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarPorSala(id));
    }
}
