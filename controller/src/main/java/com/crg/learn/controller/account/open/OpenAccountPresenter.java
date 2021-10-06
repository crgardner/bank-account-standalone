package com.crg.learn.controller.account.open;

import com.crg.learn.controller.account.shared.BasicMoneyFormatter;
import com.crg.learn.controller.presenter.BasePresenter;
import com.crg.learn.controller.view.View;
import com.crg.learn.usecase.account.open.OpenAccountResponder;
import com.crg.learn.usecase.shared.AccountResponse;

class OpenAccountPresenter extends BasePresenter implements OpenAccountResponder {

    OpenAccountPresenter(View view) {
        super(view);
    }

    @Override
    public void accept(AccountResponse response) {
        responseOf(viewModelFrom(response));
    }

    private OpenAccountViewModel viewModelFrom(AccountResponse response) {
        return new OpenAccountViewModel(response.accountNumber(),
                                    response.ownerFirstName(),
                                    response.ownerLastName(),
                                    BasicMoneyFormatter.format(response.balance()),
                                    response.balance().getCurrency().getCurrencyCode());
    }

}
