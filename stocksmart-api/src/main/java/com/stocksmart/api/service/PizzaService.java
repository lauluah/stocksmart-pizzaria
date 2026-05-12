package com.stocksmart.api.service;

import com.stocksmart.api.dto.PizzaDTO;
import com.stocksmart.api.exception.ResourceNotFoundException;
import com.stocksmart.api.model.Ingrediente;
import com.stocksmart.api.model.Pizza;
import com.stocksmart.api.model.ReceitaItem;
import com.stocksmart.api.repository.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PizzaService {

    private final PizzaRepository repository;
    private final IngredienteService ingredienteService;

    @Transactional
    public PizzaDTO.Response criar(PizzaDTO.Request dto) {
        Pizza pizza = Pizza.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .tamanho(dto.getTamanho())
                .build();

        List<ReceitaItem> itens = dto.getIngredientes().stream().map(itemDto -> {
            Ingrediente ingrediente = ingredienteService.buscarEntidade(itemDto.getIngredienteId());
            return ReceitaItem.builder()
                    .pizza(pizza)
                    .ingrediente(ingrediente)
                    .quantidade(itemDto.getQuantidade())
                    .build();
        }).collect(Collectors.toList());

        pizza.setIngredientes(itens);
        return toResponse(repository.save(pizza));
    }

    public List<PizzaDTO.Response> listarTodas() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PizzaDTO.Response buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public void deletar(Long id) {
        buscarEntidade(id);
        repository.deleteById(id);
    }


    public Pizza buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza", id));
    }

    private PizzaDTO.Response toResponse(Pizza pizza) {
        List<PizzaDTO.ReceitaItemResponse> itens = pizza.getIngredientes().stream()
                .map(item -> PizzaDTO.ReceitaItemResponse.builder()
                        .ingredienteId(item.getIngrediente().getId())
                        .ingredienteNome(item.getIngrediente().getNome())
                        .unidade(item.getIngrediente().getUnidade())
                        .quantidade(item.getQuantidade())
                        .build())
                .collect(Collectors.toList());

        BigDecimal custoTotal = pizza.getIngredientes().stream()
                .filter(item -> item.getIngrediente().getPrecoPorUnidade() != null)
                .map(item -> item.getIngrediente().getPrecoPorUnidade()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return PizzaDTO.Response.builder()
                .id(pizza.getId())
                .nome(pizza.getNome())
                .descricao(pizza.getDescricao())
                .tamanho(pizza.getTamanho())
                .ingredientes(itens)
                .custoTotal(custoTotal)
                .criadoEm(pizza.getCriadoEm())
                .build();
    }
}