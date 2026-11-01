package com.autobots.automanager.excecoes;

public class ProprietarioInvalidoException extends RuntimeException {

    public ProprietarioInvalidoException(Long usuarioId) {
        super("O usuário " + usuarioId
                + " não possui o perfil CLIENTE e não pode ser proprietário de veículo");
    }
}