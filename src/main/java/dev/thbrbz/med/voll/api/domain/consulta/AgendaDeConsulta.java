package dev.thbrbz.med.voll.api.domain.consulta;

import dev.thbrbz.med.voll.api.domain.ValidacaoException;
import dev.thbrbz.med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import dev.thbrbz.med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import dev.thbrbz.med.voll.api.domain.medico.Medico;
import dev.thbrbz.med.voll.api.domain.medico.MedicoRepository;
import dev.thbrbz.med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadoresAgendamento;

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        if (!pacienteRepository.existsById(dados.idPaciente()))
            throw new ValidacaoException("Id do paciente informado não existe!");

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico()))
            throw new ValidacaoException("Id do médico informado não existe!");

        validadoresAgendamento.forEach(validador -> validador.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);

        if (medico == null)
            throw new ValidacaoException("Não há médicos disponível nessa data!");

        var consulta = new Consulta(null, medico, paciente, null, dados.data());

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null)
            return medicoRepository.getReferenceById(dados.idMedico());

        if (dados.especialidade() == null)
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido!");

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        validadoresCancelamento.forEach(validador -> validador.validar(dados));

        var consulta = consultaRepository.findById(dados.idConsulta())
                .orElseThrow(() -> new ValidacaoException("Id da consulta informado não existe!"));

        consulta.cancelar(dados.motivo());
    }
}
