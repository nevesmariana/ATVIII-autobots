package com.autobots.automanager.excecoes;

public class ServicoNaoEncontradoException extends RuntimeException {

    public ServicoNaoEncontradoException(Long id) {
        super("Serviço não encontrado com o id: " + id);
    }
}