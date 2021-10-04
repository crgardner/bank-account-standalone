package com.crg.learn.domain.account;

import org.javamoney.moneta.Money;

import java.time.Instant;

public interface EntryImporter {
    TransactionId transactionId();

    Money amount();

    Instant whenBooked();
}
