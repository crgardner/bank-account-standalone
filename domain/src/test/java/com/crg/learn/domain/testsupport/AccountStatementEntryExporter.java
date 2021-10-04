package com.crg.learn.domain.testsupport;

import org.javamoney.moneta.Money;

import java.time.Instant;

public record AccountStatementEntryExporter(Instant whenBooked, Money amount, Money balance) {
}
