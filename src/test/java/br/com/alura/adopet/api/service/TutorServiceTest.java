package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizaTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validation.ValidacaoTutorJaCadastrado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService service;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    ValidacaoTutorJaCadastrado validacaoTutorJaCadastrado;

    private CadastroTutorDto cadastroDto;

    private AtualizaTutorDto atualizaDto;

    @Captor
    private ArgumentCaptor<Tutor> tutorCaptor;

    @Test
    @DisplayName("Sucesso ao cadastrar tutor")
    public void deveriaCadastrarTutorComSucesso() {
        cadastroDto = new CadastroTutorDto("Tutor teste", "11900009999", "teste@teste.com");

        service.cadastrar(cadastroDto);

        then(tutorRepository).should().save(tutorCaptor.capture());
        Tutor tutorSalvo = tutorCaptor.getValue();

        Assertions.assertEquals(cadastroDto.nome(), tutorSalvo.getNome());
        Assertions.assertEquals(cadastroDto.email(), tutorSalvo.getEmail());
        Assertions.assertEquals(cadastroDto.telefone(), tutorSalvo.getTelefone());
    }

    @Test
    @DisplayName("Chama validador ao cadastrar tutor")
    public void deveriaChamarValidadorTutorDuranteCadastro() {
        cadastroDto = new CadastroTutorDto("Tutor teste", "11900009999", "teste@teste.com");

        service.cadastrar(cadastroDto);

        then(validacaoTutorJaCadastrado).should().validar(cadastroDto);
    }

    @Test
    @DisplayName("Sucesso ao atualizar dados do tutor")
    public void deveriaAtualizarDadosDoTutor() {
        atualizaDto = new AtualizaTutorDto(1L, "Novo Nome", "novo@email.com", "11900000000");
        Tutor tutorAntigo = new Tutor(new CadastroTutorDto("Nome Antigo", "11888887777", "antigo@email.com"));

        given(tutorRepository.getReferenceById(atualizaDto.id())).willReturn(tutorAntigo);

        service.atualizar(atualizaDto);

        then(tutorRepository).should().getReferenceById(atualizaDto.id());

        Assertions.assertEquals(atualizaDto.nome(), tutorAntigo.getNome());
        Assertions.assertEquals(atualizaDto.email(), tutorAntigo.getEmail());
        Assertions.assertEquals(atualizaDto.telefone(), tutorAntigo.getTelefone());
    }
}