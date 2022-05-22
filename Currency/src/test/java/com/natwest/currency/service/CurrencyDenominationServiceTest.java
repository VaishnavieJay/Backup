package com.natwest.currency.service;

import com.natwest.currency.data.WalletStore;
import com.natwest.currency.exception.NWApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurrencyDenominationServiceTest {

    CurrencyDenominationService currencyDenominationService;
    private WalletStore walletStore;

    @BeforeEach
    void setUp() {
        walletStore = new WalletStore();
        currencyDenominationService = new CurrencyDenominationService(walletStore);
    }

    @Test
    void testDataWithLessMaxDenomination() throws NWApplicationException {
        final List<Integer> currencyNotesList = Arrays.asList(1, 50, 5, 5, 10, 1, 20, 20, 20, 5, 1, 50);
        walletStore.setCurrencyNotesList(currencyNotesList);
        final int amount = 183;
        final List<Integer> expectedDenomination = Arrays.asList(50, 50, 20, 20, 20, 10, 5, 5, 1, 1, 1);
        final List<Integer> actualDenomination = currencyDenominationService.getDenomination(amount);
        assertEquals(expectedDenomination, actualDenomination);
    }

    @Test
    void testGivenSampleAmount() throws NWApplicationException {
        final List<Integer> currencyNotesList = Arrays.asList(1, 50, 5, 10, 1, 20, 20, 5, 1, 50);
        walletStore.setCurrencyNotesList(currencyNotesList);
        final int amount = 123;
        final List<Integer> expectedDenomination = Arrays.asList(50, 50, 20, 1, 1, 1);
        final List<Integer> actualDenomination = currencyDenominationService.getDenomination(amount);
        assertEquals(expectedDenomination, actualDenomination);
    }

    @Test
    void testEmptyListForGivenSampleAmount() throws NWApplicationException {
        final List<Integer> currencyNotesList = Arrays.asList(10, 5, 5, 1);
        walletStore.setCurrencyNotesList(currencyNotesList);
        final int amount = 12;
        final List<Integer> expectedDenomination = new ArrayList<>();
        final List<Integer> actualDenomination = currencyDenominationService.getDenomination(amount);
        assertEquals(expectedDenomination, actualDenomination);
    }

    @Test
    void test4DigitAmount() throws NWApplicationException {
        final List<Integer> currencyNotesList = Arrays.asList(500, 100, 100, 50, 50, 100, 1, 1,
                1000, 1000, 500, 10, 5, 5, 1);
        walletStore.setCurrencyNotesList(currencyNotesList);
        final int amount = 3221;
        final List<Integer> expectedDenomination = Arrays.asList(1000, 1000, 500, 500, 100, 100, 10, 5, 5, 1);
        final List<Integer> actualDenomination = currencyDenominationService.getDenomination(amount);
        assertEquals(expectedDenomination, actualDenomination);
    }

    @Test
    void testAmountGreaterThanMoneyInWallet() throws NWApplicationException {
        final List<Integer> currencyNotesList = Arrays.asList(10, 5, 100, 20, 5, 5, 1);
        walletStore.setCurrencyNotesList(currencyNotesList);
        final int amount = 512;
        final List<Integer> expectedDenomination = new ArrayList<>();
        final List<Integer> actualDenomination = currencyDenominationService.getDenomination(amount);
        assertEquals(expectedDenomination, actualDenomination);
    }

    @Test
    void testWithAmountAsZero() throws NWApplicationException {
        final List<Integer> currencyNotesList = Arrays.asList(10, 5, 5, 1);
        walletStore.setCurrencyNotesList(currencyNotesList);
        final int amount = 0;
        final List<Integer> expectedDenomination = new ArrayList<>();
        final List<Integer> actualDenomination = currencyDenominationService.getDenomination(amount);
        assertEquals(expectedDenomination, actualDenomination);
    }

    @Test
    void testCurrencyListNull() {
        walletStore.setCurrencyNotesList(null);
        final int amount = 0;
        assertThrows(NWApplicationException.class, () -> {
            currencyDenominationService.getDenomination(amount);
        });
    }
}