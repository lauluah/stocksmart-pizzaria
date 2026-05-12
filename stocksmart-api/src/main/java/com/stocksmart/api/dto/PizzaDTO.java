package com.stocksmart.api.dto;

import com.stocksmart.api.model.Movimentacao;
import com.stocksmart.api.model.Pizza;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PizzaDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReceitaItemRequest {
        @NotNull
        private Long ingredienteId;

        @NotNull
        @DecimalMin("0.01")
        private Double quantidade;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank
        private String nome;

        private String descricao;
        private Pizza.Tamanho tamanho;

        @NotEmpty(message = "A pizza precisa ter ao menos um ingrediente")
        private List<ReceitaItemRequest> ingredientes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReceitaItemResponse {
        private Long ingredienteId;
        private String ingredienteNome;
        private String unidade;
        private Double quantidade;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String nome;
        private String descricao;
        private Pizza.Tamanho tamanho;
        private List<ReceitaItemResponse> ingredientes;
        private BigDecimal custoTotal; // calculado
        private LocalDateTime criadoEm;
    }
}


class MovimentacaoDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotNull
        private Long ingredienteId;

        @NotNull
        private Movimentacao.TipoMovimentacao tipo;

        @NotNull
        @DecimalMin("0.01")
        private Double quantidade;

        private String observacao;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String ingredienteNome;
        private Movimentacao.TipoMovimentacao tipo;
        private Double quantidade;
        private String observacao;
        private LocalDateTime criadoEm;
    }
}

class VendaDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotNull
        private Long pizzaId;

        @NotNull
        @Min(1)
        private Integer quantidade;

        private String observacao;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String pizzaNome;
        private Integer quantidade;
        private String observacao;
        private LocalDateTime criadoEm;
    }
}