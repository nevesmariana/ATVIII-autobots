package com.autobots.automanager.excecoes;

public class VeiculoNaoEncontradoException extends RuntimeException {

    public VeiculoNaoEncontradoException(Long id) {
        super("Veículo não encontrado com o id: " + id);
    }
}