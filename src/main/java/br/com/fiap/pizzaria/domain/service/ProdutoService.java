package br.com.fiap.pizzaria.domain.service;

import br.com.fiap.pizzaria.domain.dto.request.ProdutoRequest;
import br.com.fiap.pizzaria.domain.dto.response.OpcionalResponse;
import br.com.fiap.pizzaria.domain.dto.response.ProdutoResponse;
import br.com.fiap.pizzaria.domain.entity.Opcional;
import br.com.fiap.pizzaria.domain.entity.Produto;
import br.com.fiap.pizzaria.domain.entity.Sabor;
import br.com.fiap.pizzaria.domain.repository.ProdutoRepository;
import br.com.fiap.pizzaria.domain.repository.SaborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class ProdutoService implements ServiceDTO<Produto, ProdutoRequest, ProdutoResponse>{

    @Autowired
    private SaborService saborService;

    @Autowired
    private OpcionalService opcionalService;

    @Autowired
    private ProdutoRepository repo;

    @Override
    public Produto toEntity(ProdutoRequest dto) {

        Sabor sabor = null;

        sabor = saborService.findById(dto.sabor().id());
        Set<Opcional> opcionais = new LinkedHashSet<>();

        return Produto.builder()
                .nome(dto.nome())
                .preco(dto.preco())
                .sabor(sabor)
                .opcionais(opcionais)
                .build();
    }

    @Override
    public ProdutoResponse toResponse(Produto e) {
        var sabor = saborService.toResponse(e.getSabor());
        Set<OpcionalResponse> opcionais = new LinkedHashSet<>();

        return ProdutoResponse.builder()
                .id(e.getId())
                .nome(e.getNome())
                .preco(e.getPreco())
                .sabor(sabor)
                .opcionais(opcionais)
                .build();
    }
    @Override
    public Collection<Produto> findAll() {
        return repo.findAll();
    }

    @Override
    public Collection<Produto> findAll(Example<Produto> example) {
        return repo.findAll(example);
    }

    @Override
    public Produto findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Produto save(Produto e) {
        return repo.save(e);
    }
}
