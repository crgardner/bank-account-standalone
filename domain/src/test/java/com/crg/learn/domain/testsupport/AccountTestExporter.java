package com.crg.learn.domain.testsupport;

import com.crg.learn.domain.account.AccountExporter;
import org.javamoney.moneta.Money;

import java.time.Instant;
import java.util.*;

public class AccountTestExporter implements AccountExporter {

    private final List<EntryTestExporter> entries = new ArrayList<>();
    private String accountNumber;
    private String ownerFirstName;
    private String ownerLastName;
    private Money balance;

    @Override
    public void accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String accountNumber() {
        return accountNumber;
    }

    @Override
    public void ownerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String ownerFirstName() {
        return ownerFirstName;
    }

    @Override
    public void ownerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public String ownerLastName() {
        return ownerLastName;
    }

    @Override
    public void balance(Money balance) {
        this.balance = balance;
    }

    public Money balance() {
        return balance;
    }

    @Override
    public void addEntry(String transactionId, Instant whenBooked, Money amount) {
        entries.add(new EntryTestExporter(transactionId, whenBooked, amount));
    }

    public List<EntryTestExporter> entries() {
        return entries;
    }
}
