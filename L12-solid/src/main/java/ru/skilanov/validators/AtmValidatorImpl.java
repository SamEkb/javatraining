package ru.skilanov.validators;

import ru.skilanov.exceptions.NotEnoughMoneyException;
import ru.skilanov.exceptions.WrongSumException;

import static ru.skilanov.atmconstants.Denomination.*;

public class AtmValidatorImpl implements AtmValidator {

    @Override
    public void validateAtmOperation(int withdrawAmount, int totalBalance) throws WrongSumException, NotEnoughMoneyException {
        if (withdrawAmount % FIFTY.getValue() != 0) {
            throw new WrongSumException("You entered the wrong amount");
        }
        if (withdrawAmount > totalBalance) {
            throw new NotEnoughMoneyException("ATM doesn't have enough money");
        }
    }
}
