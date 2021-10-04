package com.crg.learn.persistence.account;

import com.crg.learn.domain.account.Account;
import com.crg.learn.domain.person.Person;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;

import javax.money.Monetary;

import java.time.Instant;
import java.util.*;

import static com.crg.learn.domain.testsupport.AccountMaker.*;
import static com.natpryce.makeiteasy.MakeItEasy.*;
import static org.assertj.core.api.Assertions.*;

@DisplayName("AccountEntityMapper")
class PersistentAccountMapperTest {
    private static final String ACCOUNT_NUMBER_VALUE = "9999999999";
    private static final String FIRST_NAME = "Hank";
    private static final String LAST_NAME = "Hill";
    private static final String TRANSACTION_ID = "678";

    private Instant now;
    private Account account;

    @BeforeEach
    void init() {
        now = Instant.now();

        account = make(an(Account, with(numberValue, ACCOUNT_NUMBER_VALUE),
                                   with(accountHolder, new Person(FIRST_NAME, LAST_NAME)),
                                   with(entries, listOf(an(Entry, with(whenBooked, now),
                                                                  with(entryAmount, oneHundredEuros()),
                                                                  with(transactionIdValue, TRANSACTION_ID))))));
    }

    @Test
    void mapsAccountToPersistentAccount() {
        var mapper = new PersistentAccountMapper(account);

        var persistentAccount = mapper.map();

        assertThat(persistentAccount.getAccountNumber()).isEqualTo(ACCOUNT_NUMBER_VALUE);
        assertThat(persistentAccount.getHolderFirstName()).isEqualTo(FIRST_NAME);
        assertThat(persistentAccount.getHolderLastName()).isEqualTo(LAST_NAME);
        assertThat(persistentAccount.getEntries()).usingRecursiveComparison()
                                                  .isEqualTo(expectedEntry(persistentAccount));
    }

    private Money oneHundredEuros() {
        return Money.of(100, Monetary.getCurrency("EUR"));
    }

    private List<PersistentEntry> expectedEntry(PersistentAccount persistentAccount) {
        var persistentEntry = new PersistentEntry();
        persistentEntry.setTransactionId(TRANSACTION_ID);
        persistentEntry.setAmount(oneHundredEuros());
        persistentEntry.setWhenBooked(now);

        return Collections.singletonList(persistentEntry);
    }
}