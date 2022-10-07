package ru.skilanov.model;

import ru.skilanov.atmconstants.Denomination;

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
}
