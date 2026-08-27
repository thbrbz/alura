package com.thbrbz.checkpointNivel1.controllers;

import com.thbrbz.checkpointNivel1.dto.AtualizaUsuarioDto;
import com.thbrbz.checkpointNivel1.dto.SalvaUsuarioDto;
import com.thbrbz.checkpointNivel1.dto.UsuarioDto;
import com.thbrbz.checkpointNivel1.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDto> salvar(@RequestBody @Valid SalvaUsuarioDto dto, UriComponentsBuilder uriBuilder){
        UsuarioDto usuario = usuarioService.salvar(dto);
        URI endereco = uriBuilder.path("/users/{id}").buildAndExpand(usuario.id()).toUri();

        return ResponseEntity.created(endereco).body(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarDto(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizaUsuarioDto dto) {
        return ResponseEntity.ok(usuarioService.Atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
