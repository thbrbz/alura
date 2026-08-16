package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Pet;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CadastroAbrigoDto(
        @NotBlank String nome,
        @NotBlank String telefone,
        @NotBlank String email,
        @Valid List<Pet> pets) {
}
