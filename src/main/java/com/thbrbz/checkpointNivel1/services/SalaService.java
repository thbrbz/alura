package com.thbrbz.checkpointNivel1.services;

import com.thbrbz.checkpointNivel1.dto.AtualizaSalaDto;
import com.thbrbz.checkpointNivel1.dto.SalaDto;
import com.thbrbz.checkpointNivel1.dto.SalvaSalaDto;
import com.thbrbz.checkpointNivel1.entities.Sala;
import com.thbrbz.checkpointNivel1.exceptions.SalaException;
import com.thbrbz.checkpointNivel1.repositories.SalaRepository;
import com.thbrbz.checkpointNivel1.validations.ReservaValidations;
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
    public void salvar(SalvaSalaDto dto) {
        salaRepository.save(new Sala(dto));
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
    public void Atualizar(AtualizaSalaDto dto) {
        Sala s = this.buscar(dto.id());
        s.atualizarDados(dto);
    }

    @Transactional
    public void desativar(Long id) {
        Sala s = this.buscar(id);
        s.desativarSala();
    }
}
