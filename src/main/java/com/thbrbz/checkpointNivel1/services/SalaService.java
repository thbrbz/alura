package com.thbrbz.checkpointNivel1.services;

import com.thbrbz.checkpointNivel1.dto.AtualizaSalaDto;
import com.thbrbz.checkpointNivel1.dto.SalaDto;
import com.thbrbz.checkpointNivel1.dto.SalvaSalaDto;
import com.thbrbz.checkpointNivel1.entities.Sala;
import com.thbrbz.checkpointNivel1.repositories.SalaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<SalaDto> listar(Pageable pageable) {
        return salaRepository.findAll(pageable).map(SalaDto::new);
    }

    public Sala buscar(Long id) {
        return salaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
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
