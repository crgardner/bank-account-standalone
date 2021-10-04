package com.crg.learn.controller.account.statement;

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
@Disabled
@DisplayName("PrepareStatementController")
@ExtendWith(MockitoExtension.class)
class PrepareStatementControllerTest {
    private static final String CLIENT_URI = "/banking/v1/accounts/123/statement";

    @Mock
    private PrepareAccountStatementUseCase useCase;

    @Test
    @DisplayName("controls requests to prepare statements")
    void controlsRequestsToPrepareStatements() throws Exception {
        prepare(useCase, toProvideStatement());

    }

    private Consumer<PrepareAccountStatementResponder> toProvideStatement() {
        var response = new PrepareStatementResponse(List.of(
                new PrepareStatementResponseLine(jun_21_2021(), euros_100(), euros_100()),
                new PrepareStatementResponseLine(aug_20_2021(), euros_50().negate(), euros_50())
        ));
        return responder -> responder.accept(response);
    }

    private String expectedStatementResource() {
        return """
                {
                    "lines": [ {
                            "date": "2021-06-21T16:00:00Z",
                            "type": "CREDIT",
                            "amount": "100.00",
                            "balance": "100.00"
                        }, {
                            "date": "2021-07-20T10:15:00Z",
                            "type": "DEBIT",
                            "amount": "50.00",
                            "balance": "50.00"
                        }
                   ]
                }
                """;
    }

    @Test
    @DisplayName("reports account not found")
    void reportsAccountNotFound() throws Exception {
        prepare(useCase, toReportAccountNotFound());

    }

    private Consumer<PrepareAccountStatementResponder> toReportAccountNotFound() {
        return PrepareAccountStatementResponder::onNotFound;
    }


}