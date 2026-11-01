package com.autobots.automanager.excecoes;

public class ExclusaoNaoPermitidaException extends RuntimeException {

    public ExclusaoNaoPermitidaException(String mensagem) {
        super(mensagem);
    }
}