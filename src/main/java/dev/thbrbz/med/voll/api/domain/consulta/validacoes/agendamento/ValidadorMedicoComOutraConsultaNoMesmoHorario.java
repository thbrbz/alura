package dev.thbrbz.med.voll.api.domain.consulta.validacoes.agendamento;

import dev.thbrbz.med.voll.api.domain.ValidacaoException;
import dev.thbrbz.med.voll.api.domain.consulta.ConsultaRepository;
import dev.thbrbz.med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        var medicoPossuiConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());

        if (medicoPossuiConsultaNoMesmoHorario)
            throw new ValidacaoException("Médico já possui outra consulta nesse mesmo horario!");
    }
}
