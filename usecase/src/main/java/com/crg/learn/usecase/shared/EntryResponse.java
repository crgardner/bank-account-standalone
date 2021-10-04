package com.crg.learn.usecase.shared;

import org.javamoney.moneta.Money;

import java.time.Instant;

public record EntryResponse(String transactionId, Instant whenBooked, Money amount) {
}
