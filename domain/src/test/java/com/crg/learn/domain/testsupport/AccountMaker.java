package com.crg.learn.domain.testsupport;

import com.crg.learn.domain.account.*;
import com.crg.learn.domain.person.Person;
import com.natpryce.makeiteasy.*;
import org.javamoney.moneta.Money;

import java.time.Instant;
import java.util.*;

import static com.crg.learn.domain.testsupport.MonetaryAmounts.*;
import static com.natpryce.makeiteasy.Property.*;

public class AccountMaker {
    public static final Property<Account, Person> accountHolder = newProperty();
    public static final Property<Account, AccountNumber> number = newProperty();
    public static final Property<Account, String> numberValue = newProperty();

    public static final Property<Account, Iterable<Entry>> entries = newProperty();
    public static final Property<Entry, String> transactionIdValue = newProperty();
    public static final Property<Entry, Money> entryAmount = newProperty();
    public static final Property<Entry, Instant> whenBooked = newProperty();

    public static final Instantiator<Entry> Entry = lookup ->
            new Entry(transactionIdFrom(lookup),
                      lookup.valueOf(entryAmount, zero()),
                      lookup.valueOf(whenBooked, Instant.now()));

    private static TransactionId transactionIdFrom(PropertyLookup<Entry> lookup) {
        var value = lookup.valueOf(transactionIdValue, UUID.randomUUID().toString());

        return new TransactionId(value);
    }

    public static final Instantiator<Account> Account = lookup -> {
        var account = new Account(accountNumberFrom(lookup), lookup.valueOf(accountHolder, new Person("Ford", "Prefect")));
        prepareEntries(lookup, account);

        return account;
    };

    private static void prepareEntries(PropertyLookup<Account> lookup, Account account) {
        var actualEntries = lookup.valueOf(entries, Collections.emptyList());

        actualEntries.forEach(account::add);
    }

    private static AccountNumber accountNumberFrom(PropertyLookup<Account> lookup) {
        var actualNumberValue = lookup.valueOf(numberValue, "123X99948715");

        return lookup.valueOf(number, new AccountNumber(actualNumberValue));
    }

}
