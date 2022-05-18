package com.natwest.currency.service;

import com.natwest.currency.data.WalletStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyDenominationService {

    private final WalletStore walletStore;

    public List<Integer> getDenomination(int amount) {
        final List<Integer> currencyNotesList = walletStore.getCurrencyNotesList();
        final Set<Integer> sortedCurrency = currencyNotesList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toCollection(LinkedHashSet::new));
        final List<Integer> finalDenomination = new ArrayList<>();
        int reminder, numberOfNotes;
        final Integer sum = currencyNotesList.stream().reduce(0, Integer::sum);
        if (sum < amount) {
            CurrencyDenominationService.log.error("Not enough cash in wallet");
            return new ArrayList<>();
        }
        for (final Integer denom : sortedCurrency) {
            if (amount >= denom) {
                numberOfNotes = amount / denom;
                final int notesAvailable = Collections.frequency(currencyNotesList, denom);
                if (notesAvailable >= numberOfNotes) {
                    for (int i = 0; i < numberOfNotes; i++) {
                        finalDenomination.add(denom);
                    }
                    reminder = amount % denom;
                } else {
                    for (int i = 0; i < notesAvailable; i++) {
                        finalDenomination.add(denom);
                    }
                    reminder = amount - (denom * notesAvailable);
                }
                amount = reminder;
            }
        }
        if (amount > 0) {
            CurrencyDenominationService.log.error("Not enough notes in wallet");
            return new ArrayList<>();
        }
        CurrencyDenominationService.log.info("Denomination -> " + finalDenomination);
        return finalDenomination;
    }
}
