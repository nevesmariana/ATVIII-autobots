package com.autobots.automanager.excecoes;

public class VendaNaoEncontradaException extends RuntimeException {

    public VendaNaoEncontradaException(Long id) {
        super("Venda não encontrada com o id: " + id);
    }
}