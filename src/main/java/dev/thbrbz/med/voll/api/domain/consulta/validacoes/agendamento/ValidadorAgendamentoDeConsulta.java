package dev.thbrbz.med.voll.api.domain.consulta.validacoes.agendamento;

import dev.thbrbz.med.voll.api.domain.consulta.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta {

    void validar(DadosAgendamentoConsulta dados);
}
