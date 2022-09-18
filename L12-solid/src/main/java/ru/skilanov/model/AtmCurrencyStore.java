package ru.skilanov.model;

import java.util.HashMap;
import java.util.Map;

public class AtmCurrencyStore {
    private final Map<Integer, Integer> atmCells = new HashMap();

    public Map<Integer, Integer> getAtmCells() {
        return atmCells;
    }

    public void addCell(Integer denomination, int amount) {
        atmCells.put(denomination, amount);
    }
}
