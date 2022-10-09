package ru.skilanov.exceptions;

public class WrongSumException extends Exception {
    public WrongSumException(String errorMessage) {
        super(errorMessage);
    }
}