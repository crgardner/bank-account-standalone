package com.crg.learn.usecase.account.statement;

import org.javamoney.moneta.Money;

import java.time.Instant;

public record PrepareStatementResponseLine(Instant whenBooked, Money amount, Money balance) {
}
