package com.stocksmart.api.controller;

import com.stocksmart.api.model.Venda;
import com.stocksmart.api.service.VendaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService service;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VendaRequest {
        @NotNull private Long pizzaId;
        @NotNull @Min(1) private Integer quantidade;
        private String observacao;
    }

    @PostMapping
    public ResponseEntity<Venda> registrar(@Valid @RequestBody VendaRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.registrarVenda(req.getPizzaId(), req.getQuantidade(), req.getObservacao()));
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/pizza/{id}")
    public ResponseEntity<List<Venda>> porPizza(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorPizza(id));
    }
}