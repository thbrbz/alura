package dev.thbrbz.med.voll.api.paciente;

import dev.thbrbz.med.voll.api.endereco.DadosEndereco;

public record DadosCadastroPaciente(
        String nome,
        String email,
        String telefone,
        String cpf,
        DadosEndereco endereco
) {
}
