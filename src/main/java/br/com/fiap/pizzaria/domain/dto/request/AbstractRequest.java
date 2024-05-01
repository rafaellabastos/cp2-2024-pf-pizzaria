package br.com.fiap.pizzaria.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AbstractRequest(
        @Positive(message = "O id deve ser positivo")
        @NotNull(message = "O id é obrigatório")
        Long id
) {
}
