package com.crg.learn.interactor.account.adjust;

import static org.assertj.core.api.Assertions.*;
import com.crg.learn.domain.account.Account;
import com.crg.learn.domain.account.*;
import com.crg.learn.usecase.account.adjust.*;
import com.crg.learn.usecase.concept.UseCase;
import com.crg.learn.usecase.shared.*;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.spi.MoneyUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.*;
import java.time.Instant;
import java.util.*;

import static com.crg.learn.domain.testsupport.AccountMaker.*;
import static com.natpryce.makeiteasy.MakeItEasy.*;
import static org.mockito.Mockito.*;

@DisplayName("AdjustAccountInteractor")
@ExtendWith(MockitoExtension.class)
class AdjustAccountInteractorTest implements AdjustAccountResponder {
    private static final String ACCOUNT_NUMBER = "011234567X";
    private static final String CURRENCY_VALUE = "EUR";
    private static final CurrencyUnit CURRENCY = Monetary.getCurrency(CURRENCY_VALUE);

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionIdProvider transactionIdProvider;

    private AccountResponse response;
    private AccountNumber accountNumber;
    private Account account;
    private UseCase<AdjustAccountRequest, AdjustAccountResponder> useCase;
    private boolean accountNotFound;

    @BeforeEach
    void init() {
        accountNumber = new AccountNumber(ACCOUNT_NUMBER);
        account = make(an(Account, with(number, accountNumber)));
        useCase = new AdjustAccountInteractor(accountRepository, transactionIdProvider);
    }

    @Test
    @DisplayName("makes deposits to accounts")
    void makesDepositsToAccounts() {
        when(accountRepository.lookup(accountNumber)).thenReturn(Optional.of(account));
        when(transactionIdProvider.nextTransactionId()).thenReturn(new TransactionId("abc"));

        useCase.execute(adjustRequestWithAmount(50.20), this);

        assertThat(response).usingRecursiveComparison()
                            .ignoringFieldsOfTypes(Instant.class)
                            .isEqualTo(expectedResponseWithBalance(Money.of(50.20, CURRENCY), Money.of(50.20, CURRENCY)));
        verify(accountRepository).update(account);
    }

    @Test
    @DisplayName("makes withdrawals from accounts")
    void makesWithdrawalsFromAccounts() {
        var entry = make(an(Entry, with(entryAmount, Money.of(1500, CURRENCY))));
        account.add(entry);
        when(accountRepository.lookup(accountNumber)).thenReturn(Optional.of(account));
        when(transactionIdProvider.nextTransactionId()).thenReturn(new TransactionId("abc"));

        useCase.execute(adjustRequestWithAmount(-100), this);

        assertThat(response).usingRecursiveComparison()
                            .ignoringFieldsOfTypes(Instant.class)
                            .isEqualTo(expectedResponseWithBalance(Money.of(1400, CURRENCY),
                                                                   Money.of(MoneyUtils.getBigDecimal(-100d), CURRENCY)));
        verify(accountRepository).update(account);
    }

    @Test
    @DisplayName("reports account not found when no matching account number")
    void reportsAccountNotFoundWhenNoMatchingAccountNumber() {
        when(accountRepository.lookup(accountNumber)).thenReturn(Optional.empty());

        useCase.execute(adjustRequestWithAmount(100), this);

        assertThat(accountNotFound).isTrue();
    }

    @Override
    public void accept(AccountResponse response) {
        this.response = response;
    }

    @Override
    public void onNotFound() {
        this.accountNotFound = true;
    }

    private AdjustAccountRequest adjustRequestWithAmount(double adjustmentAmount) {
        return new AdjustAccountRequest(ACCOUNT_NUMBER, adjustmentAmount, CURRENCY_VALUE);
    }

    private AccountResponse expectedResponseWithBalance(Money accountBalance, Money entryAmount) {
        return new AccountResponse(ACCOUNT_NUMBER, "Ford", "Prefect", accountBalance,
                                    Collections.singletonList(new EntryResponse("abc", Instant.now(), entryAmount)));
    }

}