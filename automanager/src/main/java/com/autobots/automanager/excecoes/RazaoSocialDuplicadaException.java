package com.autobots.automanager.excecoes;

public class RazaoSocialDuplicadaException extends RuntimeException {

    public RazaoSocialDuplicadaException(String razaoSocial) {
        super("Já existe uma empresa cadastrada com a razão social: " + razaoSocial);
    }
}