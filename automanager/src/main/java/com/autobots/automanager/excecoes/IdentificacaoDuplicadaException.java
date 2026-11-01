package com.autobots.automanager.excecoes;

public class IdentificacaoDuplicadaException extends RuntimeException {

    public IdentificacaoDuplicadaException(String identificacao) {
        super("Já existe uma venda cadastrada com a identificação: " + identificacao);
    }
}