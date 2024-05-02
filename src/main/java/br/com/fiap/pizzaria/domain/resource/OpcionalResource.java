package br.com.fiap.pizzaria.domain.resource;

import br.com.fiap.pizzaria.domain.dto.request.OpcionalRequest;
import br.com.fiap.pizzaria.domain.dto.response.OpcionalResponse;
import br.com.fiap.pizzaria.domain.entity.Opcional;
import br.com.fiap.pizzaria.domain.entity.Sabor;
import br.com.fiap.pizzaria.domain.repository.SaborRepository;
import br.com.fiap.pizzaria.domain.service.OpcionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/opcionais")
public class OpcionalResource implements ResourceDTO<OpcionalRequest, OpcionalResponse>{

    @Autowired
    SaborRepository saborRepository;

    @Autowired
    OpcionalService service;

    @GetMapping
    public ResponseEntity<Collection<OpcionalResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "preco", required = false) BigDecimal preco,
            @RequestParam(name = "sabor.id", required = false) Long idSabor,
            @RequestParam(name = "sabor.nome", required = false) String nomeSabor,
            @RequestParam(name = "sabor.descricao", required = false) String descricaoSabor
    ) {

        Sabor sabor = null;
        if (Objects.nonNull(idSabor) && idSabor > 0){
            sabor = saborRepository.findById(idSabor).orElse(null);
        }
       else {
           sabor = Sabor.builder()
                   .id(idSabor)
                   .nome(nomeSabor)
                   .descricao(descricaoSabor)
                   .build();
        }

       var opcional = Opcional.builder()
               .nome(nome)
               .preco(preco)
               .sabor(sabor)
               .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase();


        Example<Opcional> example = Example.of( opcional, matcher );

        Collection<Opcional> encontrados = service.findAll( example );

        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();

        var resposta = encontrados.stream().map( service::toResponse ).toList();


        return ResponseEntity.ok(resposta);
    }

    @Override
    @GetMapping( value = "/{id}")
    public ResponseEntity<OpcionalResponse> findById(Long id) {
        var encontrado = service.findById(id);
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse(encontrado);
        return ResponseEntity.ok(resposta);
    }

    @Override
    @PostMapping
    public ResponseEntity<OpcionalResponse> save(OpcionalRequest r) {

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
