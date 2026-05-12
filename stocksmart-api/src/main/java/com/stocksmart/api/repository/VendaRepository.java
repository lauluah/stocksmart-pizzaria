package com.stocksmart.api.repository;

import com.stocksmart.api.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByPizzaId(Long pizzaId);

    List<Venda> findByCriadoEmBetween(LocalDateTime inicio, LocalDateTime fim);
}
