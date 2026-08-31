package dev.thbrbz.med.voll.api.controller;

import dev.thbrbz.med.voll.api.medico.DadosCadastroMedido;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroMedido dados) {
        System.out.println(dados);
    }
}
