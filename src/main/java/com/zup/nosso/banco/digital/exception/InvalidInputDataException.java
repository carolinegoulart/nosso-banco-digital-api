package com.zup.nosso.banco.digital.exception;

public class InvalidInputDataException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidInputDataException(String message) {
        super(message);
    }

}
