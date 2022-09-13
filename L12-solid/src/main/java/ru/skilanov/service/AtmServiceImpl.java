package ru.skilanov.service;

import ru.skilanov.enums.CurrencyDenomination;
import ru.skilanov.exceptions.NotEnoughMoneyException;
import ru.skilanov.exceptions.WrongSumException;
import ru.skilanov.helpers.CurrencyConverter;
import ru.skilanov.model.Atm;
import ru.skilanov.validators.AtmValidator;

public class AtmServiceImpl implements AtmService {
    private final Atm atm;

    public AtmServiceImpl(Atm atm) {
        this.atm = atm;
    }

    @Override
    public void withdrawMoney(int amount) throws WrongSumException, NotEnoughMoneyException {
        AtmValidator.validateAtmOperation(amount, getBalance());
        withdraw(amount);
    }

    @Override
    public Integer getBalance() {
        return atm.getAtmCells()
                .entrySet()
                .stream()
                .map(it -> CurrencyConverter.convertDenominationToCurrency(it.getValue(), it.getKey()))
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public void contributeMoney(CurrencyDenomination denomination, int amount) {
        atm.contributeMoneyToCell(
                denomination.getValue(),
                CurrencyConverter.convertDenominationToCurrency(amount, denomination.getValue())
        );
    }

    private void withdraw(int amount) {
        var rest = amount;
        do {
            if (rest >= 5000) {
                updateCell(rest, 5000);
                rest = rest % 5000;
            } else if (rest >= 2000) {
                updateCell(rest, 2000);
                rest = rest % 2000;
            } else if (rest >= 1000) {
                updateCell(rest, 1000);
                rest = rest % 1000;
            } else if (rest >= 500) {
                updateCell(rest, 500);
                rest = rest % 500;
            } else if (rest >= 200) {
                updateCell(rest, 200);
                rest = rest % 200;
            } else if (rest >= 100) {
                updateCell(rest, 100);
                rest = rest % 100;
            } else if (rest >= 50) {
                updateCell(rest, 50);
                rest = rest % 50;
            } else if (rest >= 10) {
                updateCell(rest, 10);
                rest = rest % 10;
            }
        } while (rest != 0);
    }

    private void updateCell(int rest, int denomination) {
        atm.withdrawMoneyFromCell(denomination, rest / denomination);
    }
}
