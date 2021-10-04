package com.crg.learn.domain.account;

import com.crg.learn.domain.person.Person;
import org.javamoney.moneta.Money;

import javax.money.*;
import java.util.*;

public class Account {
    private static final CurrencyUnit DEFAULT_CURRENCY = Monetary.getCurrency("EUR");

    private final AccountNumber accountNumber;
    private final Person accountHolder;
    private final Entries entries;

    public Account(AccountNumber accountNumber, Person accountHolder) {
        this(accountNumber, accountHolder, new Entries());
    }

    private Account(AccountNumber accountNumber, Person accountHolder, Entries entries) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.entries = entries;
    }

    public Account(AccountImporter importer) {
        this(importer.accountNumber(), importer.accountHolder(), new Entries(importer.entryImporters()));
    }

    public void add(Entry entry) {
        entries.add(entry);
    }

    private Money currentBalance() {
        return entries.computeBalance(DEFAULT_CURRENCY);
    }

    public void export(AccountExporter exporter) {
        export(EntrySelectionRange.ALL, exporter);
    }

    public void export(EntrySelectionRange range, AccountExporter exporter) {
        exporter.balance(currentBalance());
        exporter.accountNumber(accountNumber.value());
        accountHolder.writeTo((first, last) -> {
            exporter.ownerFirstName(first);
            exporter.ownerLastName(last);
        });
        entries.export(range, exporter);
    }

    public AccountStatement createStatement() {
        return new AccountStatement(entries.createStatementLines(DEFAULT_CURRENCY));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (!(o instanceof Account other)) {
            return false;
        }

        return accountNumber.equals(other.accountNumber) && accountHolder.equals(other.accountHolder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, accountHolder);
    }

}
