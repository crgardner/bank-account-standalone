package com.crg.learn.domain.testsupport;

import org.javamoney.moneta.Money;

import javax.money.Monetary;

public final class MonetaryAmounts {

    private static final String DEFAULT_CURRENCY = "EUR";

    private MonetaryAmounts() {
        // no-op
    }

    public static Money amountInDefaultCurrency(Number amount) {
        return Money.of(amount, DEFAULT_CURRENCY);
    }

    public static Money zero() {
        return Money.zero(Monetary.getCurrency(DEFAULT_CURRENCY));
    }
}
