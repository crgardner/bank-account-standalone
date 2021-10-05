package com.crg.learn.controller.account.statement;

import com.crg.learn.controller.view.View;
import com.crg.learn.usecase.account.statement.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Consumer;

import static com.crg.learn.controller.test.support.BookingDates.*;
import static com.crg.learn.controller.test.support.MonetaryAmounts.*;
import static com.crg.learn.controller.test.support.UseCaseMocking.*;
import static org.mockito.Mockito.*;

@DisplayName("PrepareStatementController")
@ExtendWith(MockitoExtension.class)
class PrepareStatementControllerTest {
    private static final String ACCOUNT_NUMBER = "123";

    @Mock
    private PrepareAccountStatementUseCase useCase;

    @Mock
    private View view;

    private PrepareStatementController controller;

    @BeforeEach
    void init() {
        controller = new PrepareStatementController(useCase, view);
    }

    @Test
    @DisplayName("controls requests to prepare statements")
    void controlsRequestsToPrepareStatements() {
        prepare(useCase, toProvideStatement());

        controller.prepareStatement(ACCOUNT_NUMBER);

        verify(view).render(expectedStatementViewModel());
    }

    private Consumer<PrepareAccountStatementResponder> toProvideStatement() {
        var response = new PrepareStatementResponse(List.of(
                new PrepareStatementResponseLine(jun_21_2021(), euros_100(), euros_100()),
                new PrepareStatementResponseLine(aug_20_2021(), euros_50().negate(), euros_50())
        ));
        return responder -> responder.accept(response);
    }

    private StatementViewModel expectedStatementViewModel() {
        return new StatementViewModel(List.of(
           new Line("2021-06-21T16:00:00Z", "CREDIT", "100.00", "100.00"),
           new Line("2021-07-20T10:15:00Z", "DEBIT", "50.00", "50.00")
        ));
    }

    @Test
    @DisplayName("reports account not found")
    void reportsAccountNotFound() {
        prepare(useCase, toReportAccountNotFound());

        controller.prepareStatement(ACCOUNT_NUMBER);

        verify(view).render("not found");
    }

    private Consumer<PrepareAccountStatementResponder> toReportAccountNotFound() {
        return PrepareAccountStatementResponder::onNotFound;
    }


}