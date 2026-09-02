package com.thbrbz.checkpointNivel1.services;

import com.thbrbz.checkpointNivel1.dto.AtualizaSalaDto;
import com.thbrbz.checkpointNivel1.dto.SalaDto;
import com.thbrbz.checkpointNivel1.dto.SalvaSalaDto;
import com.thbrbz.checkpointNivel1.entities.Sala;
import com.thbrbz.checkpointNivel1.exceptions.SalaException;
import com.thbrbz.checkpointNivel1.repositories.SalaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {

    @Mock
    private SalaRepository salaRepository;

    @InjectMocks
    private SalaService salaService;

    @Mock
    private Sala sala;

    @Test
    void deveSalvarSala() {
        SalvaSalaDto salvaSalaDto = new SalvaSalaDto("Sala 1", 40L);
        sala = new Sala(salvaSalaDto);
        setField(sala, "id", 10L);

        when(salaRepository.save(any(Sala.class))).thenReturn(sala);

        SalaDto resultado = salaService.salvar(salvaSalaDto);

        assertEquals(10L, resultado.id());
        assertEquals("Sala 1", resultado.nome());
        assertTrue(resultado.ativa());
    }

    @Test
    void deveListarSalas() {
        sala = new Sala(new SalvaSalaDto("Sala 1", 40L));
        setField(sala, "id", 15L);

        when(salaRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(sala), PageRequest.of(0, 20), 1));

        Page<SalaDto> resultado = salaService.listar(PageRequest.of(0, 20));

        assertEquals(1, resultado.getTotalElements());
        assertEquals(15L, resultado.getContent().getFirst().id());
    }

    @Test
    void deveBuscarSalaPorId() {
        sala = new Sala(new SalvaSalaDto("Sala 2", 50L));
        setField(sala, "id", 21L);

        when(salaRepository.findById(21L)).thenReturn(Optional.of(sala));

        Sala resultado = salaService.buscar(21L);
        SalaDto resultadoDto = salaService.buscarDto(21L);

        assertEquals(21L, resultado.getId());
        assertEquals("Sala 2", resultadoDto.nome());
    }

    @Test
    void deveLancarExcecaoQuandoSalaNaoExiste() {
        when(salaRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> salaService.buscar(99L));

        assertTrue(exception.getMessage().contains("99"));
    }

    @Test
    void deveAtualizarSala() {
        sala = new Sala(new SalvaSalaDto("Sala Antiga", 30L));
        setField(sala, "id", 11L);

        when(salaRepository.findById(11L)).thenReturn(Optional.of(sala));

        SalaDto resultado = salaService.Atualizar(11L, new AtualizaSalaDto("Sala Nova", 60L));

        assertEquals("Sala Nova", resultado.nome());
        assertEquals(60L, resultado.capacidade());
    }

    @Test
    void deveDesativarSala() {
        sala = new Sala(new SalvaSalaDto("Sala 3", 20L));
        setField(sala, "id", 8L);

        when(salaRepository.findById(8L)).thenReturn(Optional.of(sala));

        salaService.desativar(8L);

        assertFalse(sala.isAtiva());
    }
}
