package com.crg.learn.controller.account.adjust;

import com.crg.learn.controller.view.View;
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
import static org.mockito.Mockito.*;

@DisplayName("AdjustAccountController")
@ExtendWith(MockitoExtension.class)
class AdjustAccountControllerTest {

    private static final String ACCOUNT_NUMBER = "123";
    private static final String AMOUNT = "100.00";
    private static final String BALANCE = "100.00";
    private static final String CURRENCY = "EUR";
    private static final String TRANSACTION_ID = "abc";

    @Mock
    private AdjustAccountUseCase useCase;

    @Mock
    private View view;

    private AdjustAccountController controller;

    @BeforeEach
    void init() {
        controller = new AdjustAccountController(useCase, view);
    }

    @Test
    @DisplayName("controls request to adjust accounts")
    void controlsRequestToAdjustAccounts() {
        prepare(useCase, toProvideCurrentAccountBalance());

        controller.adjustAccount(adjustAccountDetails());

        verify(view).render(expectedAdjustmentViewModel());
    }

    private AdjustAccountDetails adjustAccountDetails() {
        return new AdjustAccountDetails(ACCOUNT_NUMBER, AMOUNT, CURRENCY);
    }

    private AdjustAccountViewModel expectedAdjustmentViewModel() {
        return new AdjustAccountViewModel(ACCOUNT_NUMBER, AMOUNT, CURRENCY, TRANSACTION_ID, BALANCE);
    }

    private Consumer<AdjustAccountResponder> toProvideCurrentAccountBalance() {
        var entries = Collections.singletonList(new EntryResponse(TRANSACTION_ID, jun_21_2021(), euros_100()));

        return responder -> responder.accept(new AccountResponse(ACCOUNT_NUMBER, "Ford", "Prefect", euros_100(), entries));
    }

    @Test
    @DisplayName("reports account not found")
    void reportsAccountNotFound() {
        prepare(useCase, toReportAccountNotFound());

        controller.adjustAccount(adjustAccountDetails());

        verify(view).render("not found");
    }

    private Consumer<AdjustAccountResponder> toReportAccountNotFound() {
        return AdjustAccountResponder::onNotFound;
    }

}
