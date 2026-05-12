package com.stocksmart.api.dto;

import com.stocksmart.api.model.Ingrediente;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class IngredienteDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "Nome é obrigatório")
        private String nome;

        @NotBlank(message = "Unidade é obrigatória")
        private String unidade;

        @NotNull
        @DecimalMin("0.0")
        private Double quantidadeEstoque;

        @NotNull
        @DecimalMin("0.0")
        private Double quantidadeMinima;

        private BigDecimal precoPorUnidade;
        private LocalDate dataValidade;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String nome;
        private String unidade;
        private Double quantidadeEstoque;
        private Double quantidadeMinima;
        private BigDecimal precoPorUnidade;
        private LocalDate dataValidade;
        private boolean estoqueBaixo;
        private LocalDateTime criadoEm;
        private LocalDateTime atualizadoEm;

        public static Response fromEntity(Ingrediente i) {
            return Response.builder()
                    .id(i.getId())
                    .nome(i.getNome())
                    .unidade(i.getUnidade())
                    .quantidadeEstoque(i.getQuantidadeEstoque())
                    .quantidadeMinima(i.getQuantidadeMinima())
                    .precoPorUnidade(i.getPrecoPorUnidade())
                    .dataValidade(i.getDataValidade())
                    .estoqueBaixo(i.isEstoqueBaixo())
                    .criadoEm(i.getCriadoEm())
                    .atualizadoEm(i.getAtualizadoEm())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AtualizarQuantidadeRequest {
        @NotNull
        @DecimalMin("0.0")
        private Double quantidade;
    }
}
