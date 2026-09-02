package dev.thbrbz.med.voll.api.paciente;

import dev.thbrbz.med.voll.api.endereco.DadosEndereco;
import jakarta.validation.Valid;

public record DadosAtualizacaoPaciente(
        Long id,
        String nome,
        String telefone,
        @Valid DadosEndereco endereco
) {}
