package com.crg.learn.usecase.account.adjust;

public record AdjustAccountRequest(String accountNumber, double amount, String currency) {
}
