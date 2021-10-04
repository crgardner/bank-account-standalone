package com.crg.learn.controller.account.shared;

import org.javamoney.moneta.Money;

public final class BasicMoneyFormatter {
    private BasicMoneyFormatter() {
        // no-op
    }

    public static String formatAbs(Money amount) {
        return format(amount.abs());
    }

    public static String format(Money amount) {
        return "%.2f".formatted(amount.getNumber().doubleValueExact());
    }
}
