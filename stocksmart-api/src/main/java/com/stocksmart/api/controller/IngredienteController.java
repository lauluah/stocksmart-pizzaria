package com.stocksmart.api.controller;


import com.stocksmart.api.dto.IngredienteDTO;
import com.stocksmart.api.service.IngredienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ingredientes")
@RequiredArgsConstructor
public class IngredienteController {

    private final IngredienteService service;

    @PostMapping
    public ResponseEntity<IngredienteDTO.Response> criar(@Valid @RequestBody IngredienteDTO.Request dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<IngredienteDTO.Response>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredienteDTO.Response> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredienteDTO.Response> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody IngredienteDTO.Request dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/alertas/estoque-baixo")
    public ResponseEntity<List<IngredienteDTO.Response>> estoqueBaixo() {
        return ResponseEntity.ok(service.listarEstoqueBaixo());
    }

    @GetMapping("/alertas/vencendo")
    public ResponseEntity<List<IngredienteDTO.Response>> vencendo(
            @RequestParam(defaultValue = "7") int dias) {
        return ResponseEntity.ok(service.listarVencendo(dias));
    }
}