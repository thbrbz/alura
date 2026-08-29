package com.thbrbz.checkpointNivel1.controllers;

import com.thbrbz.checkpointNivel1.dto.AtualizaSalaDto;
import com.thbrbz.checkpointNivel1.dto.SalaDto;
import com.thbrbz.checkpointNivel1.dto.SalvaSalaDto;
import com.thbrbz.checkpointNivel1.services.SalaService;
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
class SalaControllerTest {

    @Mock
    private SalaService salaService;

    @InjectMocks
    private SalaController salaController;

    @Mock
    private SalaDto salaDto;

    @Test
    void deveSalvarSala() {
        salaDto = new SalaDto(3L, "Sala A", 30L, true);

        when(salaService.salvar(any(SalvaSalaDto.class))).thenReturn(salaDto);

        ResponseEntity<SalaDto> resultado = salaController.salvar(
                new SalvaSalaDto("Sala A", 30L),
                UriComponentsBuilder.newInstance()
        );

        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertEquals(3L, resultado.getBody().id());
    }

    @Test
    void deveListarSalas() {
        salaDto = new SalaDto(1L, "Sala 1", 20L, true);

        when(salaService.listar(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(salaDto), PageRequest.of(0, 20), 1));

        ResponseEntity<Page<SalaDto>> resultado = salaController.listar(PageRequest.of(0, 20));

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertEquals(1L, resultado.getBody().getContent().getFirst().id());
    }

    @Test
    void deveBuscarSalaPorId() {
        salaDto = new SalaDto(2L, "Sala 2", 40L, true);

        when(salaService.buscarDto(2L)).thenReturn(salaDto);

        ResponseEntity<SalaDto> resultado = salaController.buscar(2L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertEquals("Sala 2", resultado.getBody().nome());
    }

    @Test
    void deveAtualizarSala() {
        salaDto= new SalaDto(2L, "Atualizada", 22L, true);
        AtualizaSalaDto atualizaSalaDto = new AtualizaSalaDto("Atualizada", 22L);

        when(salaService.Atualizar(eq(2L), any(AtualizaSalaDto.class))).thenReturn(salaDto);

        ResponseEntity<SalaDto> resultado = salaController.atualizar(2L, atualizaSalaDto);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertEquals("Atualizada", resultado.getBody().nome());
    }

    @Test
    void deveDesativarSala() {
        ResponseEntity<String> resultado = salaController.desativar(4L);

        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
    }
}
