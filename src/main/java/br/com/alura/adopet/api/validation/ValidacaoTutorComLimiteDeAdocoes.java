package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.SolicitaAdocaoDto;
import br.com.alura.adopet.api.exception.AdocaoExeption;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoTutorComLimiteDeAdocoes implements  ValidaSolicitacaoAdocao{

    @Autowired
    TutorRepository tutorRepository;

    @Override
    public void validar(SolicitaAdocaoDto dto) {
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

        if (tutor.getAdocoes().size() >= 5)
            throw new AdocaoExeption("Tutor chegou ao limite máximo de 5 adoções!");
    }
}
