package ru.skilanov.service;

import ru.skilanov.atmconstants.Denomination;
import ru.skilanov.exceptions.NotEnoughMoneyException;
import ru.skilanov.exceptions.WrongSumException;
import ru.skilanov.model.AtmCurrencyStore;
import ru.skilanov.validators.AtmValidator;

public class AtmServiceImpl implements AtmService {
    public static final int NULL = 0;
    private final AtmValidator atmValidator;
    private final AtmCurrencyStore atmCurrencyStore;

    public AtmServiceImpl(AtmValidator atmValidator, AtmCurrencyStore atmCurrencyStore) {
        this.atmValidator = atmValidator;
        this.atmCurrencyStore = atmCurrencyStore;
    }

    @Override
    public void withdrawMoney(int amount) throws WrongSumException, NotEnoughMoneyException {
        atmValidator.validateAtmOperation(amount, getBalance());
        withdraw(amount);
    }

    @Override
    public Integer getBalance() {
        return atmCurrencyStore.getBalance();
    }

    @Override
    public void contributeMoney(Denomination denomination, int amount) {
        atmCurrencyStore.contributeMoney(denomination, amount);
    }

    private void withdraw(int amount) {
        var rest = amount;
        do {
            if (rest >= Denomination.FIVE_THOUSANDS.getValue()) {
                updateCell(rest, Denomination.FIVE_THOUSANDS);
                rest = rest % Denomination.FIVE_THOUSANDS.getValue();
            } else if (rest >= Denomination.TWO_THOUSANDS.getValue()) {
                updateCell(rest, Denomination.TWO_THOUSANDS);
                rest = rest % Denomination.TWO_THOUSANDS.getValue();
            } else if (rest >= Denomination.ONE_THOUSAND.getValue()) {
                updateCell(rest, Denomination.ONE_THOUSAND);
                rest = rest % Denomination.ONE_THOUSAND.getValue();
            } else if (rest >= Denomination.FIVE_HUNDREDS.getValue()) {
                updateCell(rest, Denomination.FIVE_HUNDREDS);
                rest = rest % Denomination.FIVE_HUNDREDS.getValue();
            } else if (rest >= Denomination.TWO_HUNDREDS.getValue()) {
                updateCell(rest, Denomination.TWO_HUNDREDS);
                rest = rest % Denomination.TWO_HUNDREDS.getValue();
            } else if (rest >= Denomination.ONE_HUNDRED.getValue()) {
                updateCell(rest, Denomination.ONE_HUNDRED);
                rest = rest % Denomination.ONE_HUNDRED.getValue();
            } else if (rest >= Denomination.FIFTY.getValue()) {
                updateCell(rest, Denomination.FIFTY);
                rest = rest % Denomination.FIFTY.getValue();
            } else if (rest >= Denomination.TEN.getValue()) {
                updateCell(rest, Denomination.TEN);
                rest = rest % Denomination.TEN.getValue();
            }
        } while (rest != NULL);
    }

    private void updateCell(int rest, Denomination denomination) {
        int amount = rest / denomination.getValue();
        atmCurrencyStore.getAtmCells().put(denomination, atmCurrencyStore.getAtmCells().get(denomination) - amount);
    }
}
