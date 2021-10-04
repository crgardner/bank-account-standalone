package com.crg.learn.domain.account;

import java.util.UUID;

public class UUIDTransactionIdProvider implements TransactionIdProvider {
    @Override
    public TransactionId nextTransactionId() {
        return new TransactionId(UUID.randomUUID().toString());
    }
}
