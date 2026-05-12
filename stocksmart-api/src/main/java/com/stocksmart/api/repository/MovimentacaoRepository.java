package com.stocksmart.api.repository;

import com.stocksmart.api.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    List<Movimentacao> findByIngredienteId(Long ingredienteId);

    List<Movimentacao> findByTipo(Movimentacao.TipoMovimentacao tipo);

    List<Movimentacao> findByCriadoEmBetween(LocalDateTime inicio, LocalDateTime fim);
}
