package com.thbrbz.checkpointNivel1.controllers;

import com.thbrbz.checkpointNivel1.dto.CriaReservaDto;
import com.thbrbz.checkpointNivel1.dto.ReservaDto;
import com.thbrbz.checkpointNivel1.entities.Sala;
import com.thbrbz.checkpointNivel1.entities.Usuario;
import com.thbrbz.checkpointNivel1.services.ReservaService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class ReservaControllerTest {

    @Mock
    private ReservaService reservaService;

    @InjectMocks
    private ReservaController reservaController;

    @Mock
    private ReservaDto reservaDto;

    @Mock
    private Sala sala;

    @Mock
    private Usuario usuario;

    @Test
    void deveCriarReserva() {
        CriaReservaDto criaReservaDto = new CriaReservaDto(
                LocalDateTime.of(2026, 8, 29, 10, 0),
                LocalDateTime.of(2026, 8, 29, 12, 0),
                1L,
                List.of(2L, 3L));

        reservaDto = new ReservaDto(10L,
                criaReservaDto.dataInicio(),
                criaReservaDto.dataFim(),
                sala,
                List.of(usuario));

        when(reservaService.criar(criaReservaDto)).thenReturn(reservaDto);

        ResponseEntity<ReservaDto> resultado = reservaController.criar(
                criaReservaDto,
                UriComponentsBuilder.newInstance()
        );

        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertEquals(10L, resultado.getBody().id());
    }

    @Test
    void deveCancelarReserva() {
        ResponseEntity<String> resultado = reservaController.cancelar(1L);

        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
    }

    @Test
    void deveListarReservas() {
        setField(sala, "id", 1L);
        setField(usuario, "id", 2L);

        reservaDto = new ReservaDto(7L,
                LocalDateTime.of(2026, 8, 29, 9, 0),
                LocalDateTime.of(2026, 8, 29, 11, 0),
                sala,
                List.of(usuario));

        when(reservaService.listar(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(reservaDto), PageRequest.of(0, 20), 1));

        ResponseEntity<Page<ReservaDto>> resultado = reservaController.listar(PageRequest.of(0, 20));

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertEquals(7L, resultado.getBody().getContent().getFirst().id());
    }

    @Test
    void deveBuscarReservaPorId() {
        setField(sala, "id", 1L);

        reservaDto = new ReservaDto(9L,
                LocalDateTime.of(2026, 8, 29, 14, 0),
                LocalDateTime.of(2026, 8, 29, 16, 0),
                sala,
                List.of());

        when(reservaService.buscarDto(9L)).thenReturn(reservaDto);

        ResponseEntity<ReservaDto> resultado = reservaController.buscar(9L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertEquals(9L, resultado.getBody().id());
    }

    @Test
    void deveBuscarReservasPorUsuario() {
        when(reservaService.buscarPorUsuario(2L)).thenReturn(List.of());

        ResponseEntity<List<ReservaDto>> resultado = reservaController.buscarPorUsuario(2L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertTrue(resultado.getBody().isEmpty());
    }

    @Test
    void deveBuscarReservasPorSala() {
        when(reservaService.buscarPorSala(5L)).thenReturn(List.of());

        ResponseEntity<List<ReservaDto>> resultado = reservaController.buscarPorSala(5L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertTrue(resultado.getBody().isEmpty());
    }
}
