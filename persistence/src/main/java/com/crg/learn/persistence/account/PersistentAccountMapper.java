package com.crg.learn.persistence.account;

import com.crg.learn.domain.account.*;
import org.javamoney.moneta.Money;

import java.time.Instant;

class PersistentAccountMapper implements AccountExporter {
    private final Account account;
    private final PersistentAccount persistentAccount = new PersistentAccount();

    public PersistentAccountMapper(Account account) {
        this.account = account;
    }

    public PersistentAccount map() {
        account.export(this);
        return persistentAccount;
    }

    @Override
    public void accountNumber(String accountNumber) {
        persistentAccount.setAccountNumber(accountNumber);
    }

    @Override
    public void ownerFirstName(String ownerFirstName) {
        persistentAccount.setHolderFirstName(ownerFirstName);
    }

    @Override
    public void ownerLastName(String ownerLastName) {
        persistentAccount.setHolderLastName(ownerLastName);
    }

    @Override
    public void balance(Money value) {
        // no-op
    }

    @Override
    public void addEntry(String transactionId, Instant whenBooked, Money amount) {
        var persistentEntry = new PersistentEntry();

        persistentEntry.setTransactionId(transactionId);
        persistentEntry.setAmount(amount);
        persistentEntry.setWhenBooked(whenBooked);

        persistentAccount.add(persistentEntry);
    }
}
