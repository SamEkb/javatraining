package ru.skilanov.exceptions;

public class NoSuchConstructorException extends RuntimeException {
    public NoSuchConstructorException(String errorMessage) {
        super(errorMessage);
    }
}