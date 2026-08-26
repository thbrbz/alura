package com.thbrbz.checkpointNivel1.services;

import com.thbrbz.checkpointNivel1.Exceptions.SalaException;
import com.thbrbz.checkpointNivel1.dto.SalaDto;
import com.thbrbz.checkpointNivel1.dto.SalvaSalaDto;
import com.thbrbz.checkpointNivel1.entities.Sala;
import com.thbrbz.checkpointNivel1.repositories.SalaRepository;
import com.thbrbz.checkpointNivel1.validations.SalaValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private List<SalaValidations> validations;

    @Transactional
    public void salvar(SalvaSalaDto dto) {
        validations.forEach(v -> v.validar(dto.dataInicio(), dto.dataFim()));
        salaRepository.save(new Sala(dto));
    }

    public List<SalaDto> getAll() {
        return salaRepository.findAll().stream().map(SalaDto::new).toList();
    }

    public SalaDto buscar(Long id) {
        return salaRepository.findById(id).map(SalaDto::new)
                .orElseThrow(() -> new SalaException("Sala não encontrada com o id: " + id));
    }

    @Transactional
    public void Atualizar(SalaDto dto) {
        validations.forEach(v -> v.validar(dto.dataInicio(), dto.dataFim()));

        Sala s = salaRepository.findById(dto.id())
                .orElseThrow(() -> new SalaException("Sala não encontrada com o id: " + dto.id()));

        s.atualizarDados(dto);
    }

    @Transactional
    public void deletar(Long id) {
        Sala s = salaRepository.findById(id)
                .orElseThrow(() -> new SalaException("Sala não encontrada com o id: " + id));

        salaRepository.delete(s);
    }
}
