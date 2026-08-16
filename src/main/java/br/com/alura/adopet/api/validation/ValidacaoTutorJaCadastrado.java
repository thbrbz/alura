package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.TutorException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoTutorJaCadastrado {

    @Autowired
    private TutorRepository tutorRepository;

    public void validar(CadastroTutorDto dto) {
        boolean telefoneOuEmailJaCadastrado = tutorRepository.existsByTelefoneOrEmail(dto.telefone(), dto.email());

        if (telefoneOuEmailJaCadastrado)
            throw new TutorException("Dados já cadastrados para outro tutor!");
    }
}
