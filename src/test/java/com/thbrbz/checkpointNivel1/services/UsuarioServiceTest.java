package com.thbrbz.checkpointNivel1.services;

import com.thbrbz.checkpointNivel1.dto.AtualizaUsuarioDto;
import com.thbrbz.checkpointNivel1.dto.SalvaUsuarioDto;
import com.thbrbz.checkpointNivel1.dto.UsuarioDto;
import com.thbrbz.checkpointNivel1.entities.Usuario;
import com.thbrbz.checkpointNivel1.exceptions.UsuarioException;
import com.thbrbz.checkpointNivel1.repositories.UsuarioRepository;
import com.thbrbz.checkpointNivel1.validations.ValidaSenha;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ValidaSenha validaSenha;

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private Usuario usuario;

    @Test
    void deveSalvarUsuario() {
        SalvaUsuarioDto salvaUsuarioDto = new SalvaUsuarioDto("Maria", "maria@email.com", "Senha@123");
        usuario = new Usuario(salvaUsuarioDto);
        setField(usuario, "id", 3L);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioDto resultado = usuarioService.salvar(salvaUsuarioDto);

        assertEquals(3L, resultado.id());
        assertEquals("Maria", resultado.nome());
        verify(validaSenha).validar(salvaUsuarioDto.senha());
    }

    @Test
    void deveListarUsuarios() {
        usuario = new Usuario(new SalvaUsuarioDto("Ana", "ana@email.com", "Senha@123"));
        setField(usuario, "id", 7L);

        when(usuarioRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(usuario), PageRequest.of(0, 20), 1));

        Page<UsuarioDto> resultado = usuarioService.listar(PageRequest.of(0, 20));

        assertEquals(1, resultado.getTotalElements());
        assertEquals(7L, resultado.getContent().getFirst().id());
    }

    @Test
    void deveBuscarUsuarioPorId() {
        usuario = new Usuario(new SalvaUsuarioDto("Pedro", "pedro@email.com", "Senha@123"));
        setField(usuario, "id", 9L);

        when(usuarioRepository.findById(9L)).thenReturn(Optional.of(usuario));

        UsuarioDto resultado = usuarioService.buscarDto(9L);

        assertEquals(9L, resultado.id());
        assertEquals("Pedro", resultado.nome());
    }

    @Test
    void deveBuscarUsuariosPorIds() {
        usuario = new Usuario(new SalvaUsuarioDto("Lucas", "lucas@email.com", "Senha@123"));
        setField(usuario, "id", 12L);

        when(usuarioRepository.findAllById(List.of(12L, 13L))).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.buscarPorIds(List.of(12L, 13L));

        assertEquals(1, resultado.size());
        assertEquals(12L, resultado.getFirst().getId());
    }

    @Test
    void deveAtualizarUsuario() {
        usuario = new Usuario(new SalvaUsuarioDto("Nome Antigo", "antigo@email.com", "Senha@123"));
        setField(usuario, "id", 14L);

        when(usuarioRepository.findById(14L)).thenReturn(Optional.of(usuario));

        UsuarioDto resultado = usuarioService
                .atualizar(14L, new AtualizaUsuarioDto("Nome Novo", "novo@email.com", "NovaSenha@456"));

        assertEquals("Nome Novo", resultado.nome());
        assertEquals("novo@email.com", resultado.email());
        verify(validaSenha).validar("NovaSenha@456");
    }

    @Test
    void deveDeletarUsuario() {
        Usuario usuario = new Usuario(new SalvaUsuarioDto("Joao", "joao@email.com", "Senha@123"));
        setField(usuario, "id", 18L);

        when(usuarioRepository.findById(18L)).thenReturn(Optional.of(usuario));

        usuarioService.deletar(18L);

        verify(usuarioRepository).delete(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> usuarioService.buscarDto(99L));

        assertTrue(exception.getMessage().contains("99"));
    }
}
