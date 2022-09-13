package ru.skilanov.helpers;

public class CurrencyConverter {

    public static int convertDenominationToCurrency(int amount, int currencyDenomination) {
        return switch (currencyDenomination) {
            case 10 -> amount * 10;
            case 50 -> amount * 50;
            case 100 -> amount * 100;
            case 200 -> amount * 200;
            case 500 -> amount * 500;
            case 1000 -> amount * 1000;
            case 2000 -> amount * 2000;
            case 5000 -> amount * 5000;
            default -> 0;
        };
    }
}
