package com.thbrbz.checkpointNivel1.services;

import com.thbrbz.checkpointNivel1.dto.SalaDto;
import com.thbrbz.checkpointNivel1.dto.SalvaSalaDto;
import com.thbrbz.checkpointNivel1.entities.Sala;
import com.thbrbz.checkpointNivel1.exceptions.SalaException;
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

    public List<SalaDto> buscarTodos() {
        return salaRepository.findAll().stream().map(SalaDto::new).toList();
    }

    public SalaDto buscarDto(Long id) {
        return new SalaDto(buscar(id));
    }

    private Sala buscar(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new SalaException("Sala não encontrada com o id: " + id));
    }

    @Transactional
    public void Atualizar(SalaDto dto) {
        validations.forEach(v -> v.validar(dto.dataInicio(), dto.dataFim()));

        Sala s = buscar(dto.id());
        s.atualizarDados(dto);
    }

    @Transactional
    public void desativar(Long id) {
        Sala s = buscar(id);
        s.desativarSala();
    }
}
