package com.crg.learn.domain.account;

import org.javamoney.moneta.Money;

import java.time.Instant;

public interface AccountExporter {
    void accountNumber(String accountNumber);

    void ownerFirstName(String ownerFirstName);

    void ownerLastName(String ownerLastName);

    void balance(Money balance);

    default void addEntry(String transactionId, Instant whenBooked, Money amount) {
        // no-op
    }
}
