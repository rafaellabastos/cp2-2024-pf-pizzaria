package br.com.fiap.pizzaria.domain.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PizzariaRequest(

        @Size(min = 2, max = 255, message = "A quantidade de caracteres deve ser entre 2 e 255")
        @NotNull(message = "O nome é obrigatório")
        String nome,

        @Valid
        @Size(min = 2, max = 255, message = "A quantidade de caracteres deve ser entre 2 e 255")
        @NotNull(message = "O cardápio é obrigatório")
        ProdutoRequest cardapio
) {
}
