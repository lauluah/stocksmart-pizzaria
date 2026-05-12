package com.stocksmart.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ingredientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false, unique = true)
    private String nome;

    @NotBlank(message = "Unidade é obrigatória")
    @Column(nullable = false)
    private String unidade;

    @NotNull(message = "Quantidade em estoque é obrigatória")
    @DecimalMin(value = "0.0", message = "Quantidade não pode ser negativa")
    @Column(nullable = false)
    private Double quantidadeEstoque;

    @NotNull(message = "Quantidade mínima é obrigatória")
    @DecimalMin(value = "0.0")
    @Column(nullable = false)
    private Double quantidadeMinima;

    @DecimalMin(value = "0.0")
    private BigDecimal precoPorUnidade;

    private LocalDate dataValidade;

    @Column(updatable = false)
    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }

    public boolean isEstoqueBaixo() {
        return this.quantidadeEstoque <= this.quantidadeMinima;
    }

    public boolean isVencendoEm(int dias) {
        if (this.dataValidade == null) return false;
        return this.dataValidade.isBefore(LocalDate.now().plusDays(dias));
    }
}