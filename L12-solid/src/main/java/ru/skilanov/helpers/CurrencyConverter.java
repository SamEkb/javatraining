package ru.skilanov.helpers;

import static ru.skilanov.atmconstants.DenominationConstants.*;

public class CurrencyConverter {

    public static int convertDenominationToCurrency(int amount, int currencyDenomination) {

        return switch (currencyDenomination) {
            case TEN -> amount * TEN;
            case FIFTY -> amount * FIFTY;
            case ONE_HUNDRED -> amount * ONE_HUNDRED;
            case TWO_HUNDREDS -> amount * TWO_HUNDREDS;
            case FIVE_HUNDREDS -> amount * FIVE_HUNDREDS;
            case ONE_THOUSAND -> amount * ONE_THOUSAND;
            case TWO_THOUSANDS -> amount * TWO_THOUSANDS;
            case FIVE_THOUSANDS -> amount * FIVE_THOUSANDS;
            default -> NULL;
        };
    }
}
