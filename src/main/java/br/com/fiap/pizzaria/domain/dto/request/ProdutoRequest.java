package br.com.fiap.pizzaria.domain.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoRequest(

        @Size(min = 2, max = 255, message = "A quantidade de caracteres deve ser entre 2 e 255")
        @NotNull(message = "O nome do produto é obrigatório")
        String nome,

        @Valid
        @NotNull(message = "O sabor é obrigatório")
        AbstractRequest sabor,

        @NotNull(message = "O preco é obrigatório")
        BigDecimal preco,

        @Valid
        AbstractRequest opcional



) {
}
