package ru.skilanov.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Atm {
    private final Map<Integer, Integer> atmCells = new HashMap();

    public Map<Integer, Integer> getAtmCells() {
        return atmCells;
    }

    public void addCell(Integer denomination, int amount) {
        atmCells.put(denomination, amount);
    }

    public void contributeMoneyToCell(Integer key, int amount) {
        atmCells.put(key, atmCells.get(key) + amount);
    }

    public void withdrawMoneyFromCell(Integer key, int amount) {
        atmCells.put(key, atmCells.get(key) - amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atm atm = (Atm) o;
        return Objects.equals(atmCells, atm.atmCells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(atmCells);
    }
}
