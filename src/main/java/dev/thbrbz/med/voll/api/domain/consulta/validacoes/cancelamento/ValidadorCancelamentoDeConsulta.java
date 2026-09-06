package dev.thbrbz.med.voll.api.domain.consulta.validacoes.cancelamento;

import dev.thbrbz.med.voll.api.domain.consulta.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {

    void validar(DadosCancelamentoConsulta dados);
}
