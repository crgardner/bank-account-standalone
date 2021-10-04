package com.crg.learn.controller.account.open;

import com.crg.learn.usecase.account.open.*;

public class OpenAccountController {
    private final OpenAccountUseCase useCase;

    public OpenAccountController(OpenAccountUseCase useCase) {
        this.useCase = useCase;
    }

    public Object openAccount(OpenAccountDetails details) {
        var request = requestFrom(details);
        var presenter = new OpenAccountPresenter();

        useCase.execute(request, presenter);

        return presenter.responseEntity();
    }

    private OpenAccountRequest requestFrom(OpenAccountDetails details) {
        return new OpenAccountRequest(details.firstName(), details.lastName());
    }
}
