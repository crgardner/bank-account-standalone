package com.crg.learn.controller.test.support;

import org.javamoney.moneta.Money;

import javax.money.Monetary;

public final class MonetaryAmounts {
    private MonetaryAmounts() {
        // no-op
    }

    public static Money euros_100() {
        return Money.of(100, Monetary.getCurrency("EUR"));
    }

    public static Money euros_50() {
        return Money.of(50, Monetary.getCurrency("EUR"));
    }

    public static Money euros_0() {
        return  Money.zero(Monetary.getCurrency("EUR"));
    }
}
