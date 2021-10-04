package com.crg.learn.domain.testsupport;

import com.crg.learn.domain.account.*;
import org.javamoney.moneta.Money;

import java.time.Instant;

public record TestEntryImporter(TransactionId transactionId, Money amount, Instant whenBooked) implements EntryImporter {
}
