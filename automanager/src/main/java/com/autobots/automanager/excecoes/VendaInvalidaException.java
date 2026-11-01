package com.autobots.automanager.excecoes;

public class VendaInvalidaException extends RuntimeException {

    public VendaInvalidaException(String mensagem) {
        super(mensagem);
    }
}