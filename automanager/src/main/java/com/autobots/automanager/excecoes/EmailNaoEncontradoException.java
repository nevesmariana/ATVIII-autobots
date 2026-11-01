package com.autobots.automanager.excecoes;

public class EmailNaoEncontradoException extends RuntimeException {

    public EmailNaoEncontradoException(Long id) {
        super("Email não encontrado com o id: " + id);
    }
}