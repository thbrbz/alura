package com.thbrbz.checkpointNivel1.services;

import com.thbrbz.checkpointNivel1.dto.AtualizaUsuarioDto;
import com.thbrbz.checkpointNivel1.dto.SalvaUsuarioDto;
import com.thbrbz.checkpointNivel1.dto.UsuarioDto;
import com.thbrbz.checkpointNivel1.entities.Usuario;
import com.thbrbz.checkpointNivel1.exceptions.UsuarioException;
import com.thbrbz.checkpointNivel1.repositories.UsuarioRepository;
import com.thbrbz.checkpointNivel1.validations.ValidaSenha;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll().stream().map(UsuarioDto::new).toList();
    }

    private Usuario buscar(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuario não encontrado com o id: " + id));
    }

    public UsuarioDto buscarDto(Long id) {
        return new UsuarioDto(this.buscar(id));
    }

    public List<Usuario> buscarPorIds(List<Long> ids) {
        return usuarioRepository.findAllById(ids);
    }

    @Transactional
    public UsuarioDto Atualizar(Long id, AtualizaUsuarioDto dto) {
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
