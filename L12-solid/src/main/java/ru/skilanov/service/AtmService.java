package ru.skilanov.service;

import ru.skilanov.atmconstants.Denomination;
import ru.skilanov.exceptions.NotEnoughMoneyException;
import ru.skilanov.exceptions.WrongSumException;

public interface AtmService {
    void withdrawMoney(int amount) throws WrongSumException, NotEnoughMoneyException;

    Integer getBalance();

    void contributeMoney(Denomination key, int amount);
}
