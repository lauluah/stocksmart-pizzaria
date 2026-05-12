package com.stocksmart.api.exception;

public class EstoqueInsuficienteException extends RuntimeException {
    public EstoqueInsuficienteException(String ingrediente, Double disponivel, Double necessario) {
        super("Estoque insuficiente para '" + ingrediente + "'. Disponível: " + disponivel + " | Necessário: " + necessario);
    }
}
