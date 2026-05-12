package com.stocksmart.api.repository;

import com.stocksmart.api.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    Optional<Ingrediente> findByNomeIgnoreCase(String nome);

    @Query("SELECT i FROM Ingrediente i WHERE i.quantidadeEstoque <= i.quantidadeMinima")
    List<Ingrediente> findEstoqueBaixo();

    @Query("SELECT i FROM Ingrediente i WHERE i.dataValidade IS NOT NULL AND i.dataValidade <= :data")
    List<Ingrediente> findVencendoAte(@org.springframework.data.repository.query.Param("data") LocalDate data);
}
