package com.crg.learn.controller.account.adjust;

import com.crg.learn.usecase.account.adjust.*;

public class AdjustAccountController {
    private final AdjustAccountUseCase useCase;

    public AdjustAccountController(AdjustAccountUseCase useCase) {
        this.useCase = useCase;
    }

    public Object adjustAccount(String accountId,
                                                AdjustAccountDetails details) {
        var request = requestFrom(accountId, details);
        var presenter = new AdjustAccountPresenter();

        useCase.execute(request, presenter);

        return presenter.responseEntity();
    }

    private AdjustAccountRequest requestFrom(String accountId, AdjustAccountDetails details) {
        return new AdjustAccountRequest(accountId, Double.parseDouble(details.amount()), details.currency());
    }

}
