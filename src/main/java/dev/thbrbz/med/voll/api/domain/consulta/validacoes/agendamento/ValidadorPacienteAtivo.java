package dev.thbrbz.med.voll.api.domain.consulta.validacoes.agendamento;

import dev.thbrbz.med.voll.api.domain.ValidacaoException;
import dev.thbrbz.med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import dev.thbrbz.med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() == null)
            return;

        var medicoEstaAtivo = pacienteRepository.findAtivoById(dados.idPaciente());

        if (!medicoEstaAtivo)
            throw  new ValidacaoException("Consulta não pode ser agendada com médico inativo!");
    }
}
