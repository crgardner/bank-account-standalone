package com.crg.learn.account.application.config;

import com.crg.learn.controller.account.adjust.AdjustAccountController;
import com.crg.learn.controller.account.open.OpenAccountController;
import com.crg.learn.controller.account.statement.PrepareStatementController;
import com.crg.learn.usecase.account.adjust.AdjustAccountUseCase;
import com.crg.learn.usecase.account.open.OpenAccountUseCase;
import com.crg.learn.usecase.account.statement.PrepareAccountStatementUseCase;

public class ControllerConfiguration {

    public OpenAccountController openAccountController(OpenAccountUseCase useCase) {
        return new OpenAccountController(useCase);
    }

    public AdjustAccountController adjustAccountController(AdjustAccountUseCase useCase) {
        return new AdjustAccountController(useCase);
    }

    public PrepareStatementController prepareStatementController(PrepareAccountStatementUseCase useCase) {
        return new PrepareStatementController(useCase);
    }
}
