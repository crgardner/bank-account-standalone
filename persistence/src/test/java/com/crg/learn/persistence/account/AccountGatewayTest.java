package com.crg.learn.persistence.account;

import com.amazonaws.services.dynamodbv2.datamodeling.AbstractDynamoDBMapper;
import com.crg.learn.domain.account.Account;
import com.crg.learn.domain.account.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.crg.learn.domain.testsupport.AccountMaker.*;
import static com.natpryce.makeiteasy.MakeItEasy.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("AccountGateway")
@ExtendWith(MockitoExtension.class)
class AccountGatewayTest {

    @Mock
    private AbstractDynamoDBMapper dynamoDb;

    @Captor
    ArgumentCaptor<PersistentAccount> savedAccount;

    private AccountGateway gateway;
    private Account account;
    private PersistentAccount persistentAccount;

    @BeforeEach
    void init() {
        gateway = new AccountGateway(dynamoDb);

        account = make(an(Account, with(numberValue, "123ABC"), with(entries, listOf(an(Entry)))));
        persistentAccount = new PersistentAccountMapper(account).map();
    }

    @Test
    @DisplayName("saves account")
    void savesAccount() {
        gateway.open(account);

        verify(dynamoDb).save(savedAccount.capture());
        assertThat(savedAccount.getValue().getAccountNumber()).isEqualTo("123ABC");
    }

    @Test
    @DisplayName("finds account by account number")
    void findsAccount() {
        when(dynamoDb.load(PersistentAccount.class, "123ABC")).thenReturn(persistentAccount);
        var possibleAccount = gateway.lookup(new AccountNumber("123ABC"));

        assertThat(possibleAccount).contains(account);
    }
}