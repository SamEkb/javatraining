package ru.skilanov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.skilanov.exceptions.NotEnoughMoneyException;
import ru.skilanov.exceptions.WrongSumException;
import ru.skilanov.model.AtmCurrencyStore;
import ru.skilanov.validators.AtmValidator;
import ru.skilanov.validators.AtmValidatorImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.skilanov.atmconstants.Denomination.*;

public class AtmServiceImplTest {

    private AtmService atmService;

    @BeforeEach
    public void setUp() {
        AtmCurrencyStore atmCurrencyStore = new AtmCurrencyStore();
        AtmValidator atmValidator = new AtmValidatorImpl();
        atmService = new AtmServiceImpl(atmValidator, atmCurrencyStore);
        atmCurrencyStore.addCell(TEN, 10);
        atmCurrencyStore.addCell(FIFTY, 20);
        atmCurrencyStore.addCell(ONE_HUNDRED, 30);
        atmCurrencyStore.addCell(TWO_HUNDREDS, 40);
        atmCurrencyStore.addCell(FIVE_HUNDREDS, 50);
        atmCurrencyStore.addCell(ONE_THOUSAND, 60);
        atmCurrencyStore.addCell(TWO_THOUSANDS, 70);
        atmCurrencyStore.addCell(FIVE_THOUSANDS, 80);
    }

    @Test
    void shouldReturnRightBalance() {
        var result = atmService.getBalance();
        assertEquals(637100, result);
    }

    @Test
    void shouldWithdrawRightAmount() throws NotEnoughMoneyException, WrongSumException {
        atmService.withdrawMoney(10500);
        var result = atmService.getBalance();
        assertEquals(626600, result);
    }

    @Test
    void shouldContributeRightAmount() {
        atmService.contributeMoney(FIFTY, 11);
        var result = atmService.getBalance();
        assertEquals(664600, result);
    }

    @Test
    void shouldThrowNotEnoughMoneyException() {
        assertThrows(NotEnoughMoneyException.class, () -> atmService.withdrawMoney(1000000));
    }

    @Test
    void shouldThrowWrongSumException() {
        assertThrows(WrongSumException.class, () -> atmService.withdrawMoney(11));
    }
}
