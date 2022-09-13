package ru.skilanov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.skilanov.enums.CurrencyDenomination;
import ru.skilanov.exceptions.NotEnoughMoneyException;
import ru.skilanov.exceptions.WrongSumException;
import ru.skilanov.model.Atm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.skilanov.enums.CurrencyDenomination.*;

public class AtmServiceImplTest {

    private Atm atm;
    private AtmService atmService;

    @BeforeEach
    public void setUp() {
        atm = new Atm();
        atmService = new AtmServiceImpl(atm);
        atm.addCell(TEN.getValue(), 10);
        atm.addCell(FIFTY.getValue(), 20);
        atm.addCell(ONE_HUNDRED.getValue(), 30);
        atm.addCell(TWO_HUNDREDS.getValue(), 40);
        atm.addCell(FIVE_HUNDREDS.getValue(), 50);
        atm.addCell(ONE_THOUSAND.getValue(), 60);
        atm.addCell(TWO_THOUSANDS.getValue(), 70);
        atm.addCell(FIVE_THOUSANDS.getValue(), 80);
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
        atmService.contributeMoney(CurrencyDenomination.FIFTY, 11);
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
