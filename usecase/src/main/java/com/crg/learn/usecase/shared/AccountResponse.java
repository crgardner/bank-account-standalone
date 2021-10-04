package com.crg.learn.usecase.shared;

import org.javamoney.moneta.Money;

import java.util.*;

public record AccountResponse(String accountNumber, String ownerFirstName, String ownerLastName, Money balance,
                              List<EntryResponse> entryResponses) {
    public AccountResponse(String accountNumber, String ownerFirstName, String ownerLastName, Money balance) {
        this(accountNumber, ownerFirstName, ownerLastName, balance, Collections.emptyList());
    }

    public Optional<EntryResponse> firstEntry() {
        return entryResponses().stream().findFirst();
    }
}
