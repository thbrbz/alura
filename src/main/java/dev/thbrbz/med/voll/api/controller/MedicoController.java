package dev.thbrbz.med.voll.api.controller;

import dev.thbrbz.med.voll.api.medico.DadosCadastroMedido;
import dev.thbrbz.med.voll.api.medico.Medico;
import dev.thbrbz.med.voll.api.medico.MedicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedido dados) {
        medicoRepository.save(new Medico(dados));
    }
}
