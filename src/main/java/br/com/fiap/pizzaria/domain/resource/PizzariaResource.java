package br.com.fiap.pizzaria.domain.resource;

import br.com.fiap.pizzaria.domain.dto.request.PizzariaRequest;
import br.com.fiap.pizzaria.domain.dto.response.OpcionalResponse;
import br.com.fiap.pizzaria.domain.dto.response.PizzariaResponse;
import br.com.fiap.pizzaria.domain.entity.Opcional;
import br.com.fiap.pizzaria.domain.entity.Pizzaria;
import br.com.fiap.pizzaria.domain.entity.Produto;
import br.com.fiap.pizzaria.domain.entity.Sabor;
import br.com.fiap.pizzaria.domain.repository.ProdutoRepository;
import br.com.fiap.pizzaria.domain.service.PizzariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(value = "/pizzaria")
public class PizzariaResource implements ResourceDTO<PizzariaRequest, PizzariaResponse>{

    @Autowired
    private PizzariaService service;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public ResponseEntity<Collection<PizzariaResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "produto.id", required = false) Long idProduto,
            @RequestParam(name = "produto.nome", required = false) String nomeProduto,
            @RequestParam(name = "produto.preco", required = false) BigDecimal precoProduto
            ){
        Produto produto = null;
        if (Objects.nonNull(idProduto) && idProduto > 0){
            produto = produtoRepository.findById(idProduto).orElse(null);
        }

        else {
            produto = Produto.builder()
                    .nome(nomeProduto)
                    .preco(precoProduto)
                    .build();

        }

        Set<Produto> cardapio = new LinkedHashSet<>();
        cardapio.add(produto);

        var pizzaria = Pizzaria.builder()
                .nome(nome)
                .cardapio(cardapio)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase();


        Example<Pizzaria> example = Example.of( pizzaria, matcher );

        Collection<Pizzaria> encontrados = service.findAll( example );

        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();

        var resposta = encontrados.stream().map( service::toResponse ).toList();
        return ResponseEntity.ok(resposta);
    }


    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<PizzariaResponse> findById(Long id) {
        var encontrado = service.findById(id);
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse(encontrado);
        return ResponseEntity.ok(resposta);
    }

    @Override
    public ResponseEntity<PizzariaResponse> save(PizzariaRequest r) {
        var entity = service.toEntity(r);
        var saved = service.save(entity);
        var resposta = service.toResponse(saved);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(uri).body(resposta);
    }
}
