package com.crg.learn.persistence.account;

import com.crg.learn.domain.account.*;
import com.crg.learn.domain.person.Person;
import org.javamoney.moneta.Money;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class AccountMapper implements AccountImporter {
    private final PersistentAccount persistentAccount;

    public AccountMapper(PersistentAccount persistentAccount) {
        this.persistentAccount = persistentAccount;
    }

    @Override
    public AccountNumber accountNumber() {
        return new AccountNumber(persistentAccount.getAccountNumber());
    }

    @Override
    public Person accountHolder() {
        return new Person(persistentAccount.getHolderFirstName(),
                          persistentAccount.getHolderLastName());
    }

    @Override
    public List<EntryImporter> entryImporters() {
        return persistentAccount.getEntries().stream().map(this::toEntryImporter).collect(Collectors.toList());
    }

    private EntryImporter toEntryImporter(PersistentEntry persistentEntry) {
        return new EntryMapper(new TransactionId(persistentEntry.getTransactionId()), persistentEntry.getAmount(),
                                                 persistentEntry.getWhenBooked());
    }

    private static record EntryMapper(TransactionId transactionId, Money amount, Instant whenBooked)
            implements EntryImporter {

    }
}
