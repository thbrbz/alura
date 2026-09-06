package dev.thbrbz.med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(
        Long id,
        Long idMedico,
        Long idPaciente,
        LocalDateTime data
) {

    public DadosDetalhamentoConsulta(Consulta c) {
        this(c.getId(), c.getMedico().getId(), c.getPaciente().getId(), c.getData());
    }
}
