package com.crg.learn.controller.account.open;

import com.crg.learn.controller.account.shared.BasicMoneyFormatter;
import com.crg.learn.controller.presenter.BasePresenter;
import com.crg.learn.usecase.account.open.OpenAccountResponder;
import com.crg.learn.usecase.shared.AccountResponse;

import java.net.URI;

class OpenAccountPresenter extends BasePresenter implements OpenAccountResponder {

    @Override
    public void accept(AccountResponse response) {
        responseOf(viewModelFrom(response));
    }

    private URI uriFrom(AccountResponse response) {
        return URI.create("/banking/accounts/v1/%s".formatted(response.accountNumber()));
    }

    private AccountViewModel viewModelFrom(AccountResponse response) {
        return new AccountViewModel(response.accountNumber(),
                                    response.ownerFirstName(),
                                    response.ownerLastName(),
                                    BasicMoneyFormatter.format(response.balance()),
                                    response.balance().getCurrency().getCurrencyCode());
    }

}
