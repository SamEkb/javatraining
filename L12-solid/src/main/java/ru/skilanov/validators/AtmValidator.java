package ru.skilanov.validators;

import ru.skilanov.exceptions.NotEnoughMoneyException;
import ru.skilanov.exceptions.WrongSumException;

public interface AtmValidator {
    void validateAtmOperation(int withdrawAmount, int totalBalance) throws WrongSumException, NotEnoughMoneyException;
}
