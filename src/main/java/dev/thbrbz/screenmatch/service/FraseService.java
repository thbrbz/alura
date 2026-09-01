package dev.thbrbz.screenmatch.service;

import dev.thbrbz.screenmatch.dto.FraseDTO;
import dev.thbrbz.screenmatch.model.Frase;
import dev.thbrbz.screenmatch.repository.FraseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FraseService {
    @Autowired
    private FraseRepository repository;

    public FraseDTO obtemFraseAleatoria() {
        Frase frase = repository.fraseAleatoria();
        return new FraseDTO(frase.getFrase(), frase.getPersonagem(), frase.getTitulo(), frase.getPoster());
    }
}
