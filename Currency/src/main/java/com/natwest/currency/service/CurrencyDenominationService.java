package com.natwest.currency.service;

import com.natwest.currency.data.WalletStore;
import com.natwest.currency.exception.NWApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyDenominationService {

    private static final long INSUFFICIENT_NOTES = 1001;
    private static final String INSUFFICIENT_NOTES_MESSAGE = "Currency Notes not initialized";
    private final WalletStore walletStore;

    public List<Integer> getDenomination(int amount) throws NWApplicationException {
        final List<Integer> currencyNotesList = walletStore.getCurrencyNotesList();
        if (CollectionUtils.isEmpty(currencyNotesList)) {
            log.error("currencyNotesList null");
            throw new NWApplicationException(INSUFFICIENT_NOTES, INSUFFICIENT_NOTES_MESSAGE);
        }
        final Set<Integer> sortedCurrency = currencyNotesList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toCollection(LinkedHashSet::new));
        final List<Integer> finalDenomination = new ArrayList<>();
        int reminder, numberOfNotes;
        final Integer sum = currencyNotesList.stream().reduce(0, Integer::sum);
        final Map<Integer, Integer> counters = currencyNotesList.stream()
                .collect(Collectors.toMap(s -> s, s -> 1, Integer::sum));
        if (sum < amount) {
            log.error("Not enough cash in wallet");
            return new ArrayList<>();
        }
        for (final Integer denom : sortedCurrency) {
            if (amount >= denom) {
                numberOfNotes = amount / denom;
                final int notesAvailable = counters.get(denom);
                if (notesAvailable >= numberOfNotes) {
                    finalDenomination.addAll(Collections.nCopies((int) numberOfNotes, denom));
                    reminder = amount % denom;
                } else {
                    finalDenomination.addAll(Collections.nCopies((int) notesAvailable, denom));
                    reminder = amount - (denom * notesAvailable);
                }
                amount = reminder;
            }
        }
        if (amount > 0) {
            log.error("Not enough notes in wallet");
            return new ArrayList<>();
        }
        log.info("Denomination -> " + finalDenomination);
        return finalDenomination;
    }
}
