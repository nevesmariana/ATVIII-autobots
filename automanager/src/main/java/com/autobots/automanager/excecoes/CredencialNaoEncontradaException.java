package com.autobots.automanager.excecoes;

public class CredencialNaoEncontradaException extends RuntimeException {

    public CredencialNaoEncontradaException(Long id) {
        super("Credencial não encontrada com o id: " + id);
    }
}