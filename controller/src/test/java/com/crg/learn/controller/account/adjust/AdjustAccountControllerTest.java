package com.crg.learn.controller.account.adjust;

import com.crg.learn.usecase.account.adjust.*;
import com.crg.learn.usecase.shared.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.function.Consumer;

import static com.crg.learn.controller.test.support.BookingDates.*;
import static com.crg.learn.controller.test.support.MonetaryAmounts.*;
import static com.crg.learn.controller.test.support.UseCaseMocking.*;

@Disabled
@DisplayName("AdjustAccountController")
@ExtendWith(MockitoExtension.class)
class AdjustAccountControllerTest {
    private static final String CLIENT_URI = "/banking/v1/accounts/123/adjustments";

    @Mock
    private AdjustAccountUseCase useCase;

    @Test
    @DisplayName("controls request to adjust accounts")
    void controlsRequestToAdjustAccounts() throws Exception {
        prepare(useCase, toProvideCurrentAccountBalance());

    }

    private String adjustAccountPostBody() {
        return """
                {
                    "amount": "100.00",
                    "currency": "EUR"
                }
                """;
    }

    private String expectedAccountResource() {
        return """
                {
                    "accountNumber": "123",
                    "balance": "100.00",
                    "currency": "EUR",
                    "transactionId": "abc",
                    "amount": "100.00"
                }
                """;
    }

    private Consumer<AdjustAccountResponder> toProvideCurrentAccountBalance() {
        var entries = Collections.singletonList(new EntryResponse("abc", jun_21_2021(), euros_100()));

        return responder -> responder.accept(new AccountResponse("123", "Ford", "Prefect", euros_100(), entries));
    }

    @Test
    @DisplayName("reports account not found")
    void reportsAccountNotFound() throws Exception {
        prepare(useCase, toReportAccountNotFound());


    }

    private Consumer<AdjustAccountResponder> toReportAccountNotFound() {
        return AdjustAccountResponder::onNotFound;
    }

}
