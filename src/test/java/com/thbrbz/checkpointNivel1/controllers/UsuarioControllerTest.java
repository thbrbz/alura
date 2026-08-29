package com.thbrbz.checkpointNivel1.controllers;

import com.thbrbz.checkpointNivel1.dto.AtualizaUsuarioDto;
import com.thbrbz.checkpointNivel1.dto.SalvaUsuarioDto;
import com.thbrbz.checkpointNivel1.dto.UsuarioDto;
import com.thbrbz.checkpointNivel1.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioDto usuarioDto;

    @Test
    void deveSalvarUsuario() {
        usuarioDto = new UsuarioDto(5L, "Maria", "maria@email.com", "Senha@123");

        when(usuarioService.salvar(any(SalvaUsuarioDto.class))).thenReturn(usuarioDto);

        ResponseEntity<UsuarioDto> resultado = usuarioController.salvar(
                new SalvaUsuarioDto("Maria", "maria@email.com", "Senha@123"),
                UriComponentsBuilder.newInstance()
        );

        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertEquals(5L, resultado.getBody().id());
    }

    @Test
    void deveListarUsuarios() {
        usuarioDto = new UsuarioDto(1L, "Joao", "joao@email.com", "Senha@123");

        when(usuarioService.listar(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(usuarioDto), PageRequest.of(0, 20), 1));

        ResponseEntity<Page<UsuarioDto>> resultado = usuarioController.listar(PageRequest.of(0, 20));

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertEquals(1L, resultado.getBody().getContent().getFirst().id());
    }

    @Test
    void deveBuscarUsuarioPorId() {
        usuarioDto = new UsuarioDto(2L, "Ana", "ana@email.com", "Senha@123");

        when(usuarioService.buscarDto(2L)).thenReturn(usuarioDto);

        ResponseEntity<UsuarioDto> resultado = usuarioController.buscar(2L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertEquals("Ana", resultado.getBody().nome());
    }

    @Test
    void deveAtualizarUsuario() {
        usuarioDto = new UsuarioDto(2L, "Ana Nova", "ana.nova@email.com", "NovaSenha@123");
        AtualizaUsuarioDto atualizaUsuarioDto = new AtualizaUsuarioDto("Ana Nova", "ana.nova@email.com", "NovaSenha@123");
        when(usuarioService.atualizar(eq(2L), any(AtualizaUsuarioDto.class))).thenReturn(usuarioDto);

        ResponseEntity<UsuarioDto> resultado = usuarioController.atualizar(2L, atualizaUsuarioDto);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertEquals("ana.nova@email.com", resultado.getBody().email());
    }

    @Test
    void deveDeletarUsuario() {
        ResponseEntity<String> resultado = usuarioController.deletar(9L);

        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
    }
}
