package dev.thbrbz.med.voll.api.controller;

import dev.thbrbz.med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import dev.thbrbz.med.voll.api.domain.consulta.AgendaDeConsulta;
import dev.thbrbz.med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import dev.thbrbz.med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsulta agendaDeConsulta;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        agendaDeConsulta.agendar(dados);
        return ResponseEntity.ok().body(new DadosDetalhamentoConsulta(null, null, null, null));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        agendaDeConsulta.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
