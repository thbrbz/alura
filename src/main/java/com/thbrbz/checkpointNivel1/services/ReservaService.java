package com.thbrbz.checkpointNivel1.services;

import com.thbrbz.checkpointNivel1.dto.CriaReservaDto;
import com.thbrbz.checkpointNivel1.dto.ReservaDto;
import com.thbrbz.checkpointNivel1.entities.Reserva;
import com.thbrbz.checkpointNivel1.entities.Sala;
import com.thbrbz.checkpointNivel1.entities.Usuario;
import com.thbrbz.checkpointNivel1.exceptions.ReservaException;
import com.thbrbz.checkpointNivel1.repositories.ReservaRepository;
import com.thbrbz.checkpointNivel1.validations.ReservaValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private SalaService salaService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private List<ReservaValidations> validations;

    @Transactional
    public ReservaDto criar(CriaReservaDto dto) {
        validations.forEach(v -> v.validar(dto));

        Sala sala = salaService.buscar(dto.salaId());
        List<Usuario> usuarios = usuarioService.buscarPorIds(dto.usuariosIds());
        Reserva reserva = new Reserva(dto.dataInicio(), dto.dataFim(), sala, usuarios);

        return new ReservaDto(reservaRepository.save(reserva));
    }

    @Transactional
    public void cancelar(Long id) {
        Reserva reserva = this.buscar(id);
        reserva.cancelar();
    }

    public List<ReservaDto> listar() {
        return reservaRepository.findAll().stream().map(ReservaDto::new).toList();
    }

    public Reserva buscar(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaException("Reserva não encontrada com o id: " + id));
    }

    public ReservaDto buscarDto(Long id) {
        return new ReservaDto(this.buscar(id));
    }

    public List<ReservaDto> buscarPorUsuario(Long id) {
        return reservaRepository.findByUsuariosId(id).stream().map(ReservaDto::new).toList();
    }

    public List<ReservaDto> buscarPorSala(Long id) {
        return reservaRepository.findBySalaId(id).stream().map(ReservaDto::new).toList();
    }
}
