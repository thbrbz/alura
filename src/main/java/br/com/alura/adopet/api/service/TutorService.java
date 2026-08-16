package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizaTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validation.ValidacaoTutorJaCadastrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private ValidacaoTutorJaCadastrado validacaoTutorJaCadastrado;

    public void cadastrar(CadastroTutorDto dto) {
        validacaoTutorJaCadastrado.validar(dto);
        tutorRepository.save(new Tutor(dto));
    }

    public void atualizar(AtualizaTutorDto dto) {
        Tutor tutor = tutorRepository.getReferenceById(dto.id());
        tutor.atualizarDados(dto);
    }
}
