package br.com.fiap.pizzaria.domain.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoRequest(

        @NotNull(message = "O nome do produto é obrigatório")
        String nome,

        @NotNull(message = "O sabor é obrigatório")
        AbstractRequest sabor,

        @NotNull(message = "O preco é obrigatório")
        BigDecimal preco,

        AbstractRequest opcional



) {
}
