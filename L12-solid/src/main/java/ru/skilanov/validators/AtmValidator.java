package ru.skilanov.validators;

import ru.skilanov.enums.CurrencyDenomination;
import ru.skilanov.exceptions.NotEnoughMoneyException;
import ru.skilanov.exceptions.WrongSumException;

public class AtmValidator {

    public static void validateAtmOperation(int withdrawAmount, int totalBalance) throws WrongSumException, NotEnoughMoneyException {
        if (withdrawAmount % CurrencyDenomination.FIFTY.getValue() != 0) {
            throw new WrongSumException("You entered the wrong amount");
        }
        if (withdrawAmount > totalBalance) {
            throw new NotEnoughMoneyException("ATM doesn't have enough money");
        }
    }
}
