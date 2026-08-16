package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.SolicitaAdocaoDto;
import br.com.alura.adopet.api.exception.AdocaoExeption;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {

    @InjectMocks
    ValidacaoTutorComLimiteDeAdocoes validacao;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    SolicitaAdocaoDto dto;

    @Mock
    private Tutor tutor;

    @Test
    @DisplayName("Permitir adoção com limite menor que a regra")
    void deveriaPermitirAdocaoComLimiteMenorQueARegra() {
        List<Adocao> adocoes = Collections.nCopies(4, new Adocao());

        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(tutor.getAdocoes()).willReturn(adocoes);

        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Lança exceção ao exceder limite de adoções")
    void deveriaLancarExceptionAoExcederLimiteDeAdocoes() {
        List<Adocao> adocoes = Collections.nCopies(6, new Adocao());

        when(tutorRepository.getReferenceById(dto.idTutor())).thenReturn(tutor);
        when(tutor.getAdocoes()).thenReturn(adocoes);

        assertThrows(AdocaoExeption.class, () -> validacao.validar(dto));
    }
}