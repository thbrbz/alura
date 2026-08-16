package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.exception.AbrigoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.validation.ValidacaoAbrigoJaCadastrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository repository;

    @Autowired
    ValidacaoAbrigoJaCadastrado validacaoAbrigoJaCadastrado;

    public List<AbrigoDto> listar() {
        return repository.findAll()
                .stream()
                .map(AbrigoDto::new)
                .toList();
    }

    public void cadastrar(CadastroAbrigoDto dto) {
        validacaoAbrigoJaCadastrado.validar(dto);

        Abrigo abrigo = new Abrigo(dto.nome(), dto.telefone(), dto.email(), dto.pets());
        repository.save(abrigo);
    }

    public List<PetDto> listarPets(String idOuNome) {
        Abrigo abrigo = buscaAbrigo(idOuNome);
        return abrigo.getPets().stream().map(PetDto::new).toList();
    }

    public Abrigo buscaAbrigo(String idOuNome) {
        Optional<Abrigo> optional;

        try {
            Long id = Long.parseLong(idOuNome);
            optional = repository.findById(id);
        } catch (NumberFormatException e) {
            optional = repository.findByNome(idOuNome);
        }

        return optional.orElseThrow(() -> new AbrigoException("Abrigo não encontrado!"));
    }
}
