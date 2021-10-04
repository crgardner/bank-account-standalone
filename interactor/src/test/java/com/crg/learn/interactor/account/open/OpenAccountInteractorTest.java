package com.crg.learn.interactor.account.open;

import static org.assertj.core.api.Assertions.*;
import com.crg.learn.domain.account.Account;
import com.crg.learn.domain.account.*;
import com.crg.learn.usecase.account.open.*;
import com.crg.learn.usecase.shared.AccountResponse;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.Monetary;

import static com.crg.learn.domain.testsupport.AccountMaker.*;
import static com.natpryce.makeiteasy.MakeItEasy.*;
import static org.mockito.Mockito.*;

@DisplayName("OpenAccountInteractor")
@ExtendWith(MockitoExtension.class)
class OpenAccountInteractorTest implements OpenAccountResponder {
    private static final String ACCOUNT_NUMBER = "011234567X";

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountNumberProvider accountNumberProvider;

    private AccountResponse response;

    @Test
    @DisplayName("creates an account")
    void createsAccount() {
        when(accountNumberProvider.nextAccountNumber()).thenReturn(new AccountNumber(ACCOUNT_NUMBER));
        var request = new OpenAccountRequest("Ford", "Prefect");
        var useCase = new OpenAccountInteractor(accountRepository, accountNumberProvider);

        useCase.execute(request, this);

        assertThat(response).isEqualTo(expectedResponse());
        verify(accountRepository).open(expectedAccount());
    }

    private Account expectedAccount() {
        return make(an(Account, with(numberValue, ACCOUNT_NUMBER)));
    }

    private AccountResponse expectedResponse() {
        return new AccountResponse(ACCOUNT_NUMBER, "Ford", "Prefect",
                                   Money.zero(Monetary.getCurrency("EUR")));
    }

    @Override
    public void accept(AccountResponse response) {
        this.response = response;
    }
}