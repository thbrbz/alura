package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.SolicitaAdocaoDto;

public interface ValidaSolicitacaoAdocao {

    void validar(SolicitaAdocaoDto dto);
}
