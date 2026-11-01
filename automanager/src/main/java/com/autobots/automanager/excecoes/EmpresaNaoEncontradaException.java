package com.autobots.automanager.excecoes;

public class EmpresaNaoEncontradaException extends RuntimeException {

    public EmpresaNaoEncontradaException(Long id) {
        super("Empresa não encontrada com o id: " + id);
    }
}