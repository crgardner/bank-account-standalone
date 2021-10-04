package com.crg.learn.persistence.account;

import com.crg.learn.domain.account.Account;
import com.crg.learn.domain.account.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.crg.learn.domain.testsupport.AccountMaker.*;
import static com.natpryce.makeiteasy.MakeItEasy.*;
import static org.assertj.core.api.Assertions.*;

@Disabled
@DisplayName("AccountGateway")
@ExtendWith(MockitoExtension.class)
class AccountGatewayTest {

    private AccountGateway adapter;
    private Account account;
    private PersistentAccount persistentAccount;

    @BeforeEach
    void init() {
        adapter = new AccountGateway();
        account = make(an(Account, with(entries, listOf(an(Entry)))));
        persistentAccount = new PersistentAccountMapper(account).map();
    }

    @Test
    @DisplayName("saves account")
    void savesAccount() {
        adapter.open(account);

    }

    @Test
    @DisplayName("finds account by account number")
    void findsAccount() {

        var possibleAccount = adapter.lookup(new AccountNumber("123"));

        assertThat(possibleAccount).contains(account);
    }
}