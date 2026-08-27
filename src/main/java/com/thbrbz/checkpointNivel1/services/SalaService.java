package com.thbrbz.checkpointNivel1.services;

import com.thbrbz.checkpointNivel1.dto.AtualizaSalaDto;
import com.thbrbz.checkpointNivel1.dto.SalaDto;
import com.thbrbz.checkpointNivel1.dto.SalvaSalaDto;
import com.thbrbz.checkpointNivel1.entities.Sala;
import com.thbrbz.checkpointNivel1.exceptions.SalaException;
import com.thbrbz.checkpointNivel1.repositories.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    @Transactional
    public SalaDto salvar(SalvaSalaDto dto) {
        Sala sala = new Sala(dto);
        return new SalaDto(salaRepository.save(sala));
    }

    public List<SalaDto> buscarTodos() {
        return salaRepository.findAll().stream().map(SalaDto::new).toList();
    }

    public Sala buscar(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new SalaException("Sala não encontrada com o id: " + id));
    }

    public SalaDto buscarDto(Long id) {
        return new SalaDto(this.buscar(id));
    }

    @Transactional
    public SalaDto Atualizar(Long id, AtualizaSalaDto dto) {
        Sala s = this.buscar(id);
        s.atualizarDados(dto);
        return new SalaDto(s);
    }

    @Transactional
    public void desativar(Long id) {
        Sala s = this.buscar(id);
        s.desativarSala();
    }
}
