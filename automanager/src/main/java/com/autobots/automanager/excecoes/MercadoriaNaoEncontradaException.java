package com.autobots.automanager.excecoes;

public class MercadoriaNaoEncontradaException extends RuntimeException {

    public MercadoriaNaoEncontradaException(Long id) {
        super("Mercadoria não encontrada com o id: " + id);
    }
}