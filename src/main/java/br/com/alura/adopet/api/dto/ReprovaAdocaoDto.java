package br.com.alura.adopet.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReprovaAdocaoDto(@NotNull Long idAdocao, @NotBlank String justificativa) {
}
