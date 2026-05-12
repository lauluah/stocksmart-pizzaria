package com.stocksmart.api.service;


import com.stocksmart.api.dto.IngredienteDTO;
import com.stocksmart.api.exception.ResourceNotFoundException;
import com.stocksmart.api.model.Ingrediente;
import com.stocksmart.api.repository.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredienteService {

    private final IngredienteRepository repository;


    @Transactional
    public IngredienteDTO.Response criar(IngredienteDTO.Request dto) {
        Ingrediente ingrediente = Ingrediente.builder()
                .nome(dto.getNome())
                .unidade(dto.getUnidade())
                .quantidadeEstoque(dto.getQuantidadeEstoque())
                .quantidadeMinima(dto.getQuantidadeMinima())
                .precoPorUnidade(dto.getPrecoPorUnidade())
                .dataValidade(dto.getDataValidade())
                .build();
        return IngredienteDTO.Response.fromEntity(repository.save(ingrediente));
    }

    public List<IngredienteDTO.Response> listarTodos() {
        return repository.findAll().stream()
                .map(IngredienteDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    public IngredienteDTO.Response buscarPorId(Long id) {
        return IngredienteDTO.Response.fromEntity(buscarEntidade(id));
    }

    @Transactional
    public IngredienteDTO.Response atualizar(Long id, IngredienteDTO.Request dto) {
        Ingrediente ingrediente = buscarEntidade(id);
        ingrediente.setNome(dto.getNome());
        ingrediente.setUnidade(dto.getUnidade());
        ingrediente.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        ingrediente.setQuantidadeMinima(dto.getQuantidadeMinima());
        ingrediente.setPrecoPorUnidade(dto.getPrecoPorUnidade());
        ingrediente.setDataValidade(dto.getDataValidade());
        return IngredienteDTO.Response.fromEntity(repository.save(ingrediente));
    }

    @Transactional
    public void deletar(Long id) {
        buscarEntidade(id);
        repository.deleteById(id);
    }


    public List<IngredienteDTO.Response> listarEstoqueBaixo() {
        return repository.findEstoqueBaixo().stream()
                .map(IngredienteDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    public List<IngredienteDTO.Response> listarVencendo(int dias) {
        LocalDate limite = LocalDate.now().plusDays(dias);
        return repository.findVencendoAte(limite).stream()
                .map(IngredienteDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }


    public Ingrediente buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingrediente", id));
    }

    @Transactional
    public void debitar(Ingrediente ingrediente, Double quantidade) {
        ingrediente.setQuantidadeEstoque(ingrediente.getQuantidadeEstoque() - quantidade);
        repository.save(ingrediente);
    }

    @Transactional
    public void creditar(Ingrediente ingrediente, Double quantidade) {
        ingrediente.setQuantidadeEstoque(ingrediente.getQuantidadeEstoque() + quantidade);
        repository.save(ingrediente);
    }
}