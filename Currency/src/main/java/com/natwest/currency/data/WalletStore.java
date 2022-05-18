package com.natwest.currency.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class WalletStore {
    List<Integer> currencyNotesList;
}
