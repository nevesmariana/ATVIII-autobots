package com.autobots.automanager.excecoes;

public class DatasMercadoriaInvalidasException extends RuntimeException {

    public DatasMercadoriaInvalidasException() {
        super("A data de validade deve ser posterior à data de fabricação");
    }
}