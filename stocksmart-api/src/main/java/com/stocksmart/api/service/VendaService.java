package com.stocksmart.api.service;

import com.stocksmart.api.exception.EstoqueInsuficienteException;
import com.stocksmart.api.model.Ingrediente;
import com.stocksmart.api.model.Pizza;
import com.stocksmart.api.model.ReceitaItem;
import com.stocksmart.api.model.Venda;
import com.stocksmart.api.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final PizzaService pizzaService;
    private final IngredienteService ingredienteService;

    @Transactional
    public Venda registrarVenda(Long pizzaId, Integer quantidade, String observacao) {
        Pizza pizza = pizzaService.buscarEntidade(pizzaId);

        for (ReceitaItem item : pizza.getIngredientes()) {
            Ingrediente ingrediente = item.getIngrediente();
            double necessario = item.getQuantidade() * quantidade;

            if (ingrediente.getQuantidadeEstoque() < necessario) {
                throw new EstoqueInsuficienteException(
                        ingrediente.getNome(),
                        ingrediente.getQuantidadeEstoque(),
                        necessario);
            }
        }

        for (ReceitaItem item : pizza.getIngredientes()) {
            Ingrediente ingrediente = ingredienteService.buscarEntidade(item.getIngrediente().getId());
            double totalDebitar = item.getQuantidade() * quantidade;
            ingredienteService.debitar(ingrediente, totalDebitar);
        }

        Venda venda = Venda.builder()
                .pizza(pizza)
                .quantidade(quantidade)
                .observacao(observacao)
                .build();

        return vendaRepository.save(venda);
    }

    public List<Venda> listarTodas() {
        return vendaRepository.findAll();
    }

    public List<Venda> listarPorPizza(Long pizzaId) {
        return vendaRepository.findByPizzaId(pizzaId);
    }
}