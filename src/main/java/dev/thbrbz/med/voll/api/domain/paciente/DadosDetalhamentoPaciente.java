package dev.thbrbz.med.voll.api.domain.paciente;

import dev.thbrbz.med.voll.api.domain.endereco.Endereco;

public record DadosDetalhamentoPaciente(
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone,
        Boolean ativo,
        Endereco endereco
) {

    public DadosDetalhamentoPaciente(Paciente p) {
        this(p.getId(), p.getNome(), p.getEmail(), p.getCpf(), p.getTelefone(), p.getAtivo(), p.getEndereco());
    }
}
