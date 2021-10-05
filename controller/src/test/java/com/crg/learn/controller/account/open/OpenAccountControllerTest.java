package com.crg.learn.controller.account.open;

import com.crg.learn.controller.view.View;
import com.crg.learn.usecase.account.open.*;
import com.crg.learn.usecase.shared.AccountResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Consumer;

import static com.crg.learn.controller.test.support.MonetaryAmounts.*;
import static com.crg.learn.controller.test.support.UseCaseMocking.*;
import static org.mockito.Mockito.*;

@DisplayName("OpenAccountController")
@ExtendWith(MockitoExtension.class)
class OpenAccountControllerTest {

    private static final String ACCOUNT_NUMBER = "123";
    private static final String FIRST_NAME = "Ford";
    private static final String LAST_NAME = "Prefect";

    @Mock
    private OpenAccountUseCase useCase;

    @Mock
    private View view;

    private OpenAccountController controller;

    @BeforeEach
    void init() {
        controller = new OpenAccountController(useCase, view);
    }

    @Test
    @DisplayName("controls request to open accounts")
    void controlsRequestToOpenAccounts() {
        prepare(useCase, toProvideNewlyOpenedAccount());

        controller.openAccount(new OpenAccountDetails(FIRST_NAME, LAST_NAME));

        verify(view).render(expectedAccountViewModel());
    }

    private Consumer<OpenAccountResponder> toProvideNewlyOpenedAccount() {
        return responder -> responder.accept(new AccountResponse(ACCOUNT_NUMBER, FIRST_NAME, LAST_NAME, euros_0()));
    }

    private OpenAccountViewModel expectedAccountViewModel() {
        return new OpenAccountViewModel(ACCOUNT_NUMBER, FIRST_NAME, LAST_NAME, "0.00", "EUR");
    }

}