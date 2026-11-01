package com.autobots.automanager.excecoes;

public class AssociacaoJaExistenteException extends RuntimeException {

    public AssociacaoJaExistenteException(String mensagem) {
        super(mensagem);
    }
}