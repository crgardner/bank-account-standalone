package com.crg.learn.controller.account.open;

import com.crg.learn.usecase.account.open.*;
import com.crg.learn.usecase.shared.AccountResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Consumer;

import static com.crg.learn.controller.test.support.MonetaryAmounts.*;
import static com.crg.learn.controller.test.support.UseCaseMocking.*;
@Disabled
@DisplayName("OpenAccountController")
@ExtendWith(MockitoExtension.class)
class OpenAccountControllerTest {
    private static final String CLIENT_URI = "/banking/v1/accounts";

    @Mock
    private OpenAccountUseCase useCase;

    @Test
    @DisplayName("controls request to open accounts")
    void controlsRequestToOpenAccounts() throws Exception {
        prepare(useCase, toProvideNewlyOpenedAccount());

    }

    private Consumer<OpenAccountResponder> toProvideNewlyOpenedAccount() {
        return responder -> responder.accept(new AccountResponse("123", "Ford", "Prefect", euros_0()));
    }

    private String openAccountPostBody() {
        return """
                 {
                    "firstName": "Ford",
                    "lastName": "Prefect"
                 }
                """;
    }

    private String expectedAccountResource() {
        return """
                {
                    "accountNumber": "123",
                    "firstName": "Ford",
                    "lastName": "Prefect",
                    "balance": "0.00";
                    "currency": "EUR"
                }
                """;
    }

}