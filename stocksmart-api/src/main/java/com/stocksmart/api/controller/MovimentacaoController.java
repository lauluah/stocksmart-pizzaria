package com.stocksmart.api.controller;

import com.stocksmart.api.model.Movimentacao;
import com.stocksmart.api.service.MovimentacaoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService service;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovimentacaoRequest {
        @NotNull private Long ingredienteId;
        @NotNull private Movimentacao.TipoMovimentacao tipo;
        @NotNull @DecimalMin("0.01") private Double quantidade;
        private String observacao;
    }

    @PostMapping
    public ResponseEntity<Movimentacao> registrar(@Valid @RequestBody MovimentacaoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.registrar(req.getIngredienteId(), req.getTipo(), req.getQuantidade(), req.getObservacao()));
    }

    @GetMapping
    public ResponseEntity<List<Movimentacao>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/ingrediente/{id}")
    public ResponseEntity<List<Movimentacao>> porIngrediente(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorIngrediente(id));
    }

    @GetMapping("/desperdicios")
    public ResponseEntity<List<Movimentacao>> desperdicios() {
        return ResponseEntity.ok(service.listarDesperdicios());
    }
}