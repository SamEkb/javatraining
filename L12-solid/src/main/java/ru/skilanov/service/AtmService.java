package ru.skilanov.service;

import ru.skilanov.atmconstants.CurrencyDenomination;
import ru.skilanov.exceptions.NotEnoughMoneyException;
import ru.skilanov.exceptions.WrongSumException;

public interface AtmService {
    void withdrawMoney(int amount) throws WrongSumException, NotEnoughMoneyException;

    Integer getBalance();

    void contributeMoney(CurrencyDenomination key, int amount);
}
