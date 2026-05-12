package com.stocksmart.api.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String recurso, Long id) {
        super(recurso + " com id " + id + " não encontrado(a).");
    }
    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }
}
