package dev.thbrbz.med.voll.api.medico;

import dev.thbrbz.med.voll.api.endereco.Endereco;

public record DadosDetalhamentoMedico(
        Long id,
        String nome,
        String email,
        String crm,
        String telefone,
        Especialidade especialidade,
        Endereco endereco
) {

    public DadosDetalhamentoMedico(Medico m) {
        this(m.getId(), m.getNome(), m.getEmail(), m.getCrm(), m.getTelefone(), m.getEspecialidade(), m.getEndereco());
    }
}
