package com.crg.learn.domain.account;

import java.util.UUID;

public class UUIDAccountNumberProvider implements AccountNumberProvider {

    @Override
    public AccountNumber nextAccountNumber() {
        return new AccountNumber(UUID.randomUUID().toString());
    }
}
