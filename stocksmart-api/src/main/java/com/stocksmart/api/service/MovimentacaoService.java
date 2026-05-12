package com.stocksmart.api.service;

import com.stocksmart.api.exception.EstoqueInsuficienteException;
import com.stocksmart.api.model.Ingrediente;
import com.stocksmart.api.model.Movimentacao;
import com.stocksmart.api.repository.MovimentacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final MovimentacaoRepository repository;
    private final IngredienteService ingredienteService;

    @Transactional
    public Movimentacao registrar(Long ingredienteId, Movimentacao.TipoMovimentacao tipo,
                                  Double quantidade, String observacao) {

        Ingrediente ingrediente = ingredienteService.buscarEntidade(ingredienteId);


        if (tipo == Movimentacao.TipoMovimentacao.SAIDA ||
                tipo == Movimentacao.TipoMovimentacao.DESPERDICIO) {

            if (ingrediente.getQuantidadeEstoque() < quantidade) {
                throw new EstoqueInsuficienteException(
                        ingrediente.getNome(),
                        ingrediente.getQuantidadeEstoque(),
                        quantidade);
            }
            ingredienteService.debitar(ingrediente, quantidade);
        } else {
            ingredienteService.creditar(ingrediente, quantidade);
        }

        Movimentacao mov = Movimentacao.builder()
                .ingrediente(ingrediente)
                .tipo(tipo)
                .quantidade(quantidade)
                .observacao(observacao)
                .build();

        return repository.save(mov);
    }

    public List<Movimentacao> listarTodas() {
        return repository.findAll();
    }

    public List<Movimentacao> listarPorIngrediente(Long ingredienteId) {
        return repository.findByIngredienteId(ingredienteId);
    }

    public List<Movimentacao> listarDesperdicios() {
        return repository.findByTipo(Movimentacao.TipoMovimentacao.DESPERDICIO);
    }
}