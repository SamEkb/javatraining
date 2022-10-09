package ru.skilanov.helpers;

import ru.skilanov.atmconstants.Denomination;

import static ru.skilanov.atmconstants.Denomination.*;

public class CurrencyConverter {

    public static int convertDenominationToCurrency(int amount, Denomination currencyDenomination) {


        return switch (currencyDenomination) {
            case TEN -> amount * TEN.getValue();
            case FIFTY -> amount * FIFTY.getValue();
            case ONE_HUNDRED -> amount * ONE_HUNDRED.getValue();
            case TWO_HUNDREDS -> amount * TWO_HUNDREDS.getValue();
            case FIVE_HUNDREDS -> amount * FIVE_HUNDREDS.getValue();
            case ONE_THOUSAND -> amount * ONE_THOUSAND.getValue();
            case TWO_THOUSANDS -> amount * TWO_THOUSANDS.getValue();
            case FIVE_THOUSANDS -> amount * FIVE_THOUSANDS.getValue();
        };
    }
}
