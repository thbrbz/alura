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
    public void salvar(SalvaUsuarioDto dto) {
        validaSenha.validar(dto.senha());
        usuarioRepository.save(new Usuario(dto));
    }

    public List<UsuarioDto> buscarTodos() {
        return usuarioRepository.findAll().stream().map(UsuarioDto::new).toList();
    }

    public UsuarioDto buscarDto(Long id) {
        return new UsuarioDto(buscar(id));
    }

    private Usuario buscar(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuario não encontrado com o id: " + id));
    }

    @Transactional
    public void Atualizar(AtualizaUsuarioDto dto) {
        validaSenha.validar(dto.senha());

        Usuario u = buscar(dto.id());
        u.atualizarDados(dto);
    }

    @Transactional
    public void deletar(Long id) {
        Usuario u = buscar(id);
        usuarioRepository.delete(u);
    }
}
