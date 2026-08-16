package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.SolicitaAdocaoDto;
import br.com.alura.adopet.api.exception.AdocaoExeption;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoPetComAdocaoEmAndamento implements ValidaSolicitacaoAdocao {

    @Autowired
    private AdocaoRepository adocaoRepository;

    @Override
    public void validar(SolicitaAdocaoDto dto) {
        boolean petComAdocaoEmAndamento = adocaoRepository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO);

        if (petComAdocaoEmAndamento)
            throw new AdocaoExeption("Pet já está aguardando avaliação para ser adotado!");
    }
}
