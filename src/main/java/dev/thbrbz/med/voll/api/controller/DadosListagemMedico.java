package dev.thbrbz.med.voll.api.controller;

import dev.thbrbz.med.voll.api.medico.Especialidade;
import dev.thbrbz.med.voll.api.medico.Medico;

public record DadosListagemMedico(
        String nome,
        String email,
        String crm,
        Especialidade especialidade
) {

    public DadosListagemMedico(Medico m) {
        this(m.getNome(), m.getEmail(), m.getCrm(), m.getEspecialidade());
    }
}
