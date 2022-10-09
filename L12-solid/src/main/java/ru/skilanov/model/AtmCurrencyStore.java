package ru.skilanov.model;

import ru.skilanov.atmconstants.Denomination;
import ru.skilanov.helpers.CurrencyConverter;

import java.util.HashMap;
import java.util.Map;

public class AtmCurrencyStore {
    private final Map<Denomination, Integer> atmCells = new HashMap<>();

    public Map<Denomination, Integer> getAtmCells() {
        return atmCells;
    }

    public void addCell(Denomination denomination, int amount) {
        atmCells.put(denomination, amount);
    }

    public int getBalance() {
        return getAtmCells()
                .entrySet()
                .stream()
                .map(it -> CurrencyConverter.convertDenominationToCurrency(it.getValue(), it.getKey()))
                .mapToInt(Integer::intValue)
                .sum();
    }

    public void contributeMoney(Denomination denomination, int amount) {
        int currencyAmount = CurrencyConverter.convertDenominationToCurrency(amount, denomination);
        getAtmCells().put(
                denomination, getAtmCells().get(denomination) + currencyAmount
        );
    }
}
