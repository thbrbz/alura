package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AprovaAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovaAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitaAdocaoDto;
import br.com.alura.adopet.api.exception.AdocaoExeption;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validation.ValidaSolicitacaoAdocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdocaoService {

    @Autowired
    private AdocaoRepository repository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private List<ValidaSolicitacaoAdocao> validacoes;

    @Autowired
    private EmailService emailService;

    public void solicitar(SolicitaAdocaoDto dto) {
        Pet pet = petRepository.findById(dto.idPet()).orElseThrow(() -> new AdocaoExeption("Pet não encontrado com o id: " + dto.idPet()));
        Tutor tutor = tutorRepository.findById(dto.idTutor()).orElseThrow(() -> new AdocaoExeption("Tutor não encontrado com o id: " + dto.idTutor()));

        validacoes.forEach(v-> v.validar(dto));

        Adocao adocao = new Adocao(tutor, pet, dto.motivo());
        repository.save(adocao);

        emailService.enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Solicitação de adoção",
                "Olá " +adocao.getPet().getAbrigo().getNome() +"!\n\nUma solicitação de adoção foi registrada hoje para o pet: " +adocao.getPet().getNome() +". \nFavor avaliar para aprovação ou reprovação.");
    }

    public void aprovar(AprovaAdocaoDto dto) {
        Adocao adocao = repository.findById(dto.idAdocao()).orElseThrow(() -> new AdocaoExeption("Adoção não encontrada com o id: " + dto.idAdocao()));
        adocao.setStatus(StatusAdocao.APROVADO);

        emailService.enviarEmail(
                adocao.getTutor().getEmail(),
                "Adoção aprovada",
                "Parabéns " +adocao.getTutor().getNome() +"!\n\nSua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi aprovada.\nFavor entrar em contato com o abrigo " +adocao.getPet().getAbrigo().getNome() +" para agendar a busca do seu pet.");
    }

    public void reprovar(ReprovaAdocaoDto dto) {
        Adocao adocao = repository.findById(dto.idAdocao()).orElseThrow(() -> new AdocaoExeption("Adoção não encontrada com o id: " + dto.idAdocao()));
        adocao.setStatus(StatusAdocao.REPROVADO);
        adocao.setJustificativaStatus(dto.justificativa());

        emailService.enviarEmail(
                adocao.getTutor().getEmail(),
                "Adoção reprovada",
                "Olá " +adocao.getTutor().getNome() +"!\n\nInfelizmente sua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi reprovada pelo abrigo " +adocao.getPet().getAbrigo().getNome() +" com a seguinte justificativa: " +adocao.getJustificativaStatus());
    }
}
