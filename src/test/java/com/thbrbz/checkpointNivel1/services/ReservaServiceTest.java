package com.thbrbz.checkpointNivel1.services;

import com.thbrbz.checkpointNivel1.dto.CriaReservaDto;
import com.thbrbz.checkpointNivel1.dto.ReservaDto;
import com.thbrbz.checkpointNivel1.entities.Reserva;
import com.thbrbz.checkpointNivel1.entities.Sala;
import com.thbrbz.checkpointNivel1.entities.Usuario;
import com.thbrbz.checkpointNivel1.enums.ReservaStatus;
import com.thbrbz.checkpointNivel1.exceptions.ReservaException;
import com.thbrbz.checkpointNivel1.repositories.ReservaRepository;
import com.thbrbz.checkpointNivel1.validations.ReservaValidations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private SalaService salaService;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private ReservaService reservaService;

    @Mock
    private Sala sala;

    @Mock
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        setField(reservaService, "validations", List.of(mock(ReservaValidations.class)));
    }

    @Test
    void deveCriarReservaComDadosValidos() {
        CriaReservaDto criaReservaDto = new CriaReservaDto(
                LocalDateTime.of(2026, 8, 29, 10, 0),
                LocalDateTime.of(2026, 8, 29, 12, 0),
                5L,
                List.of(1L, 2L));

        List<Usuario> usuarios = List.of(new Usuario(), new Usuario());

        when(salaService.buscar(5L)).thenReturn(sala);
        when(usuarioService.buscarPorIds(criaReservaDto.usuariosIds())).thenReturn(usuarios);

        reserva = new Reserva(criaReservaDto.dataInicio(), criaReservaDto.dataFim(), sala, usuarios);
        setField(reserva, "id", 10L);

        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        ReservaDto resultado = reservaService.criar(criaReservaDto);

        assertNotNull(resultado);
        assertEquals(10L, resultado.id());
        verify(salaService).buscar(5L);
        verify(usuarioService).buscarPorIds(criaReservaDto.usuariosIds());
        verify(reservaRepository).save(any(Reserva.class));
    }

    @Test
    void deveCancelarReserva() {
        reserva = new Reserva(LocalDateTime.now(), LocalDateTime.now().plusHours(2), sala, List.of());

        when(reservaRepository.findById(7L)).thenReturn(Optional.of(reserva));

        reservaService.cancelar(7L);

        assertEquals(ReservaStatus.CANCELADA, reserva.getStatus());
    }

    @Test
    void deveListarReservas() {
        reserva = new Reserva(LocalDateTime.now(), LocalDateTime.now().plusHours(2), sala, List.of());
        setField(reserva, "id", 11L);

        when(reservaRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(reserva),PageRequest.of(0, 20), 1));

        Page<ReservaDto> resultado = reservaService.listar(PageRequest.of(0, 20));

        assertEquals(1, resultado.getTotalElements());
        assertEquals(11L, resultado.getContent().getFirst().id());
    }

    @Test
    void deveBuscarReservaPorId() {
        reserva = new Reserva(LocalDateTime.now(), LocalDateTime.now().plusHours(2), sala, List.of());
        setField(reserva, "id", 21L);

        when(reservaRepository.findById(21L)).thenReturn(Optional.of(reserva));

        Reserva resultado = reservaService.buscar(21L);
        ReservaDto resultadoDto = reservaService.buscarDto(21L);

        assertEquals(21L, resultado.getId());
        assertEquals(21L, resultadoDto.id());
    }

    @Test
    void deveLancarExcecaoQuandoReservaNaoExiste() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        ReservaException exception = assertThrows(ReservaException.class, () -> reservaService.buscar(99L));

        assertTrue(exception.getMessage().contains("99"));
    }

    @Test
    void deveBuscarReservasPorUsuario() {
        reserva = new Reserva(LocalDateTime.now(), LocalDateTime.now().plusHours(2), sala, List.of());
        setField(reserva, "id", 35L);

        when(reservaRepository.findByUsuariosId(4L)).thenReturn(List.of(reserva));

        List<ReservaDto> resultado = reservaService.buscarPorUsuario(4L);

        assertEquals(1, resultado.size());
        assertEquals(35L, resultado.getFirst().id());
    }

    @Test
    void deveBuscarReservasPorSala() {
        reserva = new Reserva(LocalDateTime.now(), LocalDateTime.now().plusHours(2), sala, List.of());
        setField(reserva, "id", 42L);

        when(reservaRepository.findBySalaId(8L)).thenReturn(List.of(reserva));

        List<ReservaDto> resultado = reservaService.buscarPorSala(8L);

        assertEquals(1, resultado.size());
        assertEquals(42L, resultado.getFirst().id());
    }
}
