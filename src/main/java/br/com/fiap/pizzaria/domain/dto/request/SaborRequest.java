package br.com.fiap.pizzaria.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SaborRequest(
        @Size(min = 5, max = 255, message = "A quantidade de caracteres da descrição deve estar entre")
        @NotNull(message = "A descrição é campo obrigatório")
        String descricao,

        @Size(min = 2, max = 255, message = "A quantidade de caracteres da descrição deve estar entre")
        @NotNull(message = "O nome é campo obrigatório")
        String nome
) {}
