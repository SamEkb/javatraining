package ru.skilanov.service;

import ru.skilanov.atmconstants.CurrencyDenomination;
import ru.skilanov.exceptions.NotEnoughMoneyException;
import ru.skilanov.exceptions.WrongSumException;
import ru.skilanov.helpers.CurrencyConverter;
import ru.skilanov.model.AtmCurrencyStore;
import ru.skilanov.validators.AtmValidator;

import static ru.skilanov.atmconstants.DenominationConstants.*;

public class AtmServiceImpl implements AtmService {
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
        return atmCurrencyStore.getAtmCells()
                .entrySet()
                .stream()
                .map(it -> CurrencyConverter.convertDenominationToCurrency(it.getValue(), it.getKey()))
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public void contributeMoney(CurrencyDenomination denomination, int amount) {
        int key = denomination.getValue();
        int currencyAmount = CurrencyConverter.convertDenominationToCurrency(amount, key);
        atmCurrencyStore.getAtmCells().put(
                key, atmCurrencyStore.getAtmCells().get(key) + currencyAmount
        );
    }

    private void withdraw(int amount) {
        var rest = amount;
        do {
            if (rest >= FIVE_THOUSANDS) {
                updateCell(rest, FIVE_THOUSANDS);
                rest = rest % FIVE_THOUSANDS;
            } else if (rest >= TWO_THOUSANDS) {
                updateCell(rest, TWO_THOUSANDS);
                rest = rest % TWO_THOUSANDS;
            } else if (rest >= ONE_THOUSAND) {
                updateCell(rest, ONE_THOUSAND);
                rest = rest % ONE_THOUSAND;
            } else if (rest >= FIVE_HUNDREDS) {
                updateCell(rest, FIVE_HUNDREDS);
                rest = rest % FIVE_HUNDREDS;
            } else if (rest >= TWO_HUNDREDS) {
                updateCell(rest, TWO_HUNDREDS);
                rest = rest % TWO_HUNDREDS;
            } else if (rest >= ONE_HUNDRED) {
                updateCell(rest, ONE_HUNDRED);
                rest = rest % ONE_HUNDRED;
            } else if (rest >= FIFTY) {
                updateCell(rest, FIFTY);
                rest = rest % FIFTY;
            } else if (rest >= TEN) {
                updateCell(rest, TEN);
                rest = rest % TEN;
            }
        } while (rest != NULL);
    }

    private void updateCell(int rest, int denomination) {
        int amount = rest / denomination;
        atmCurrencyStore.getAtmCells().put(denomination, atmCurrencyStore.getAtmCells().get(denomination) - amount);
    }
}
