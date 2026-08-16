package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.SolicitaAdocaoDto;
import br.com.alura.adopet.api.exception.AdocaoExeption;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoPetDisponivel implements ValidaSolicitacaoAdocao {

    @Autowired
    private PetRepository petRepository;

    @Override
    public void validar(SolicitaAdocaoDto dto) {
        Pet pet = petRepository.getReferenceById(dto.idPet());

        if (pet.getAdotado())
            throw new AdocaoExeption("Pet já foi adotado!");
    }
}
