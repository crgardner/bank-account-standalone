package com.crg.learn.controller.account.adjust;

import com.crg.learn.controller.view.View;
import com.crg.learn.usecase.account.adjust.*;

public class AdjustAccountController {
    private final AdjustAccountUseCase useCase;
    private final View view;

    public AdjustAccountController(AdjustAccountUseCase useCase, View view) {
        this.useCase = useCase;
        this.view = view;
    }

    public void adjustAccount(AdjustAccountDetails details) {
        var request = requestFrom(details);
        var presenter = new AdjustAccountPresenter(view);

        useCase.execute(request, presenter);
    }

    private AdjustAccountRequest requestFrom(AdjustAccountDetails details) {
        return new AdjustAccountRequest(details.accountNumber(), Double.parseDouble(details.amount()),
                                        details.currency());
    }

}
