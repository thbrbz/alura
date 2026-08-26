package com.thbrbz.checkpointNivel1.controllers;

import com.thbrbz.checkpointNivel1.dto.AtualizaUsuarioDto;
import com.thbrbz.checkpointNivel1.dto.SalvaUsuarioDto;
import com.thbrbz.checkpointNivel1.dto.UsuarioDto;
import com.thbrbz.checkpointNivel1.exceptions.UsuarioException;
import com.thbrbz.checkpointNivel1.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> salvar(@Valid @RequestBody SalvaUsuarioDto dto){
        try {
            usuarioService.salvar(dto);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário salvo com sucesso!");
        } catch (UsuarioException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<UsuarioDto>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.buscarDto(id));
        } catch (UsuarioException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizar(@Valid @RequestBody AtualizaUsuarioDto dto) {
        try {
            usuarioService.Atualizar(dto);
            return ResponseEntity.ok("Usuário atualizado com sucesso!");
        } catch (UsuarioException e) {
            return ResponseEntity.unprocessableContent().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        try {
            usuarioService.deletar(id);
            return ResponseEntity.ok().body("Usuário %s excluído com sucesso!".formatted(id));
        } catch (UsuarioException e) {
            return ResponseEntity.unprocessableContent().body(e.getMessage());
        }
    }
}
