package dev.thbrbz.med.voll.api.medico;

import dev.thbrbz.med.voll.api.endereco.DadosEndereco;

public record DadosCadastroMedido(
        String nome,
        String email,
        String crm,
        Especialidade especialidade,
        DadosEndereco endereco
) {
}
