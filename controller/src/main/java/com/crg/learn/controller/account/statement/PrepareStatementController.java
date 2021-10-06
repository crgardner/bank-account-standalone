package com.crg.learn.controller.account.statement;

import com.crg.learn.controller.view.View;
import com.crg.learn.usecase.account.statement.*;

public class PrepareStatementController {
    private final PrepareAccountStatementUseCase useCase;
    private final View view;

    public PrepareStatementController(PrepareAccountStatementUseCase useCase, View view) {
        this.useCase = useCase;
        this.view = view;
    }

    public void prepareStatement(String accountNumber) {
        var presenter = new PrepareAccountStatementPresenter(view);

        useCase.execute(new PrepareAccountStatementRequest(accountNumber), presenter);
    }
}
