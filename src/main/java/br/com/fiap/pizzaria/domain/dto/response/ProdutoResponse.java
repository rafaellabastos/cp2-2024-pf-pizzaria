package br.com.fiap.pizzaria.domain.dto.response;

import java.math.BigDecimal;
import java.util.Collection;

public record ProdutoResponse(
        Long id,
        String nome,
        BigDecimal preco,
        SaborResponse sabor,
        Collection<OpcionalResponse> opcionais
) {
}
