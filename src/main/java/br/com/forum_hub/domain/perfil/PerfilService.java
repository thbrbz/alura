package br.com.forum_hub.domain.perfil;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public PerfilService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    public Perfil buscarPorNome(PerfilNome nome) {
        return perfilRepository.findByNome(nome)
                .orElseThrow(() -> new EntityNotFoundException("Perfil não encontrado por nome: " + nome));
    }
}
