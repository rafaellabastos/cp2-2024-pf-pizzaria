package br.com.fiap.pizzaria.domain.resource;

import br.com.fiap.pizzaria.domain.dto.request.SaborRequest;
import br.com.fiap.pizzaria.domain.dto.response.SaborResponse;
import br.com.fiap.pizzaria.domain.repository.SaborRepository;
import br.com.fiap.pizzaria.domain.service.SaborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/sabores")
public class SaborResource implements ResourceDTO<SaborRequest, SaborResponse> {

    @Autowired
    private SaborService service;

    @GetMapping
    public ResponseEntity<Collection<SaborResponse>> findAll (
            @RequestParam(name = "sabor.id", required = false) Long idSabor
    ) {
        var sabor = Objects.nonNull( idSabor ) ? SaborRepository
                .findById( idSabor )
                .orElse()
    }
}
