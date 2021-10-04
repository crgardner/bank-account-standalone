package com.crg.learn.interactor.account.shared;

import com.crg.learn.domain.account.*;
import com.crg.learn.usecase.shared.*;
import org.javamoney.moneta.Money;

import java.time.Instant;
import java.util.*;

public class AccountResponseBuilder implements AccountExporter {
    private final List<EntryResponse> entries = new ArrayList<>();
    private String accountNumberValue;
    private String firstNameValue;
    private String lastNameValue;
    private Money balanceValue;

    @Override
    public void accountNumber(String accountNumberValue) {
        this.accountNumberValue = accountNumberValue;
    }

    @Override
    public void ownerFirstName(String firstNameValue) {
        this.firstNameValue = firstNameValue;
    }

    @Override
    public void ownerLastName(String lastNameValue) {
        this.lastNameValue = lastNameValue;
    }

    @Override
    public void balance(Money balanceValue) {
        this.balanceValue = balanceValue;
    }

    @Override
    public void addEntry(String transactionId, Instant whenBooked, Money amount) {
        entries.add(new EntryResponse(transactionId, whenBooked, amount));
    }

    public AccountResponse build() {
        return new AccountResponse(accountNumberValue, firstNameValue, lastNameValue, balanceValue, entries);
    }
}
