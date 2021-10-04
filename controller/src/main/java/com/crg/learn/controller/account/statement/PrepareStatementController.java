package com.crg.learn.controller.account.statement;

import com.crg.learn.usecase.account.statement.*;

public class PrepareStatementController {
    private final PrepareAccountStatementUseCase useCase;

    public PrepareStatementController(PrepareAccountStatementUseCase useCase) {
        this.useCase = useCase;
    }

    public Object prepareStatement(String accountId) {
        var presenter = new PrepareAccountStatementPresenter();
        useCase.execute(new PrepareAccountStatementRequest(accountId), presenter);

        return presenter.responseEntity();
    }
}
