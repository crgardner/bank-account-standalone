package com.crg.learn.domain.account;

public class TransactionId {
    private final String value;

    public TransactionId(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
