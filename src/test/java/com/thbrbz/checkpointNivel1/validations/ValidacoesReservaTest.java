package com.thbrbz.checkpointNivel1.validations;

import com.thbrbz.checkpointNivel1.dto.CriaReservaDto;
import com.thbrbz.checkpointNivel1.entities.Sala;
import com.thbrbz.checkpointNivel1.exceptions.ReservaException;
import com.thbrbz.checkpointNivel1.repositories.ReservaRepository;
import com.thbrbz.checkpointNivel1.services.SalaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class ValidacoesReservaTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private SalaService salaService;

    @Mock
    private Sala sala;

    @Mock
    private CriaReservaDto criaReservaDto;

    @Test
    void deveValidarDataFimMaiorQueInicio() {
        ValidaDataFimMaiorQueInicio validacao = new ValidaDataFimMaiorQueInicio();

        criaReservaDto = new CriaReservaDto(
                LocalDateTime.of(2026, 8, 29, 12, 0),
                LocalDateTime.of(2026, 8, 29, 10, 0),
                1L,
                List.of(1L));

        ReservaException exception = assertThrows(ReservaException.class, () -> validacao.validar(criaReservaDto));
        assertTrue(exception.getMessage().contains("A data de término deve ser posterior à data de início"));
    }

    @Test
    void deveValidarDuracaoMinimaDaReserva() {
        ValidaDuracaoMinimaDaReserva validacao = new ValidaDuracaoMinimaDaReserva();

        criaReservaDto = new CriaReservaDto(
                LocalDateTime.of(2026, 8, 29, 10, 0),
                LocalDateTime.of(2026, 8, 29, 11, 30),
                1L,
                List.of(1L));

        ReservaException exception = assertThrows(ReservaException.class, () -> validacao.validar(criaReservaDto));
        assertTrue(exception.getMessage().contains("Duração da reserva não pode ser menor que duas horas!"));
    }

    @Test
    void deveValidarChoqueDeHorario() {
        ValidaChoqueDeHorario validacao = new ValidaChoqueDeHorario();
        setField(validacao, "reservaRepository", reservaRepository);

        criaReservaDto = new CriaReservaDto(
                LocalDateTime.of(2026, 8, 29, 9, 0),
                LocalDateTime.of(2026, 8, 29, 12, 0),
                1L,
                List.of(1L));

        when(reservaRepository.existeConflitoDeHorario(
                eq(1L),
                any(),
                eq(criaReservaDto.dataInicio()),
                eq(criaReservaDto.dataFim()))
        ).thenReturn(true);

        ReservaException exception = assertThrows(ReservaException.class, () -> validacao.validar(criaReservaDto));
        assertTrue(exception.getMessage().contains("Já existe uma reserva ativa para essa sala no horário informado!"));
    }

    @Test
    void deveValidarSalaAtiva() {
        ValidaSeSalaInativa validacao = new ValidaSeSalaInativa();
        setField(validacao, "salaService", salaService);
        setField(sala, "ativa", false);

        criaReservaDto = new CriaReservaDto(
                LocalDateTime.of(2026, 8, 29, 9, 0),
                LocalDateTime.of(2026, 8, 29, 12, 0),
                1L,
                List.of(1L));

        when(salaService.buscar(1L)).thenReturn(sala);

        ReservaException exception = assertThrows(ReservaException.class, () -> validacao.validar(criaReservaDto));
        assertTrue(exception.getMessage().contains("Sala informada não pode ser usada por estar inativa!"));
    }
}
