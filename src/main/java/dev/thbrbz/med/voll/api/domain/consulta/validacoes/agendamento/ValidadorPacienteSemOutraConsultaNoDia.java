package dev.thbrbz.med.voll.api.domain.consulta.validacoes.agendamento;

import dev.thbrbz.med.voll.api.domain.ValidacaoException;
import dev.thbrbz.med.voll.api.domain.consulta.ConsultaRepository;
import dev.thbrbz.med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        var pacientePossuiConsultaNoDia = consultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);

        if (pacientePossuiConsultaNoDia)
            throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia!");
    }
}
