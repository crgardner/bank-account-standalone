package com.crg.learn.controller.account.open;

import com.crg.learn.controller.view.View;
import com.crg.learn.usecase.account.open.*;

public class OpenAccountController {
    private final OpenAccountUseCase useCase;
    private final View view;

    public OpenAccountController(OpenAccountUseCase useCase, View view) {
        this.useCase = useCase;
        this.view = view;
    }

    public void openAccount(OpenAccountDetails details) {
        var request = requestFrom(details);
        var presenter = new OpenAccountPresenter(view);

        useCase.execute(request, presenter);
    }

    private OpenAccountRequest requestFrom(OpenAccountDetails details) {
        return new OpenAccountRequest(details.firstName(), details.lastName());
    }
}
