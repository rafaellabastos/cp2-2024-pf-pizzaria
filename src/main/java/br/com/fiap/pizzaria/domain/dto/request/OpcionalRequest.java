package br.com.fiap.pizzaria.domain.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record OpcionalRequest(
        @Size(min = 2, max = 255)
        @NotNull(message = "Nome é um campo obrigatório")
        String nome,

        @Valid
        AbstractRequest sabor,

        @NotNull(message = "Preço é um campo obrigatório")
        BigDecimal preco
) {}
