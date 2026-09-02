package com.thbrbz.checkpointNivel1.services;

import com.thbrbz.checkpointNivel1.dto.AtualizaUsuarioDto;
import com.thbrbz.checkpointNivel1.dto.SalvaUsuarioDto;
import com.thbrbz.checkpointNivel1.dto.UsuarioDto;
import com.thbrbz.checkpointNivel1.entities.Usuario;
import com.thbrbz.checkpointNivel1.repositories.UsuarioRepository;
import com.thbrbz.checkpointNivel1.validations.ValidaSenha;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ValidaSenha validaSenha;

    @Transactional
    public UsuarioDto salvar(SalvaUsuarioDto dto) {
        validaSenha.validar(dto.senha());
        Usuario usuario = usuarioRepository.save(new Usuario(dto));
        return new UsuarioDto(usuario);
    }

    public Page<UsuarioDto> listar(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(UsuarioDto::new);
    }

    private Usuario buscar(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrada com o ID: " + id));
    }

    public UsuarioDto buscarDto(Long id) {
        return new UsuarioDto(this.buscar(id));
    }

    public List<Usuario> buscarPorIds(List<Long> ids) {
        return usuarioRepository.findAllById(ids);
    }

    @Transactional
    public UsuarioDto atualizar(Long id, AtualizaUsuarioDto dto) {
        validaSenha.validar(dto.senha());

        Usuario u = this.buscar(id);
        u.atualizarDados(dto);

        return new UsuarioDto(u);
    }

    @Transactional
    public void deletar(Long id) {
        Usuario u = this.buscar(id);
        usuarioRepository.delete(u);
    }
}
