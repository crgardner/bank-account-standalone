package com.crg.learn.controller.account.adjust;

public record AdjustmentViewModel(String accountNumber, String balance, String currency, String transactionId,
                                  String amount) {
}
