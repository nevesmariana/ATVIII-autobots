package com.autobots.automanager.excecoes;

public class CredencialDuplicadaException extends RuntimeException {

    public CredencialDuplicadaException(String valor) {
        super("Já existe uma credencial cadastrada com: " + valor);
    }
}