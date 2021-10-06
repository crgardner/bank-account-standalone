package com.crg.learn.controller.account.adjust;

import com.crg.learn.controller.account.shared.BasicMoneyFormatter;
import com.crg.learn.controller.presenter.BasePresenter;
import com.crg.learn.controller.view.View;
import com.crg.learn.usecase.account.adjust.AdjustAccountResponder;
import com.crg.learn.usecase.shared.*;
import org.javamoney.moneta.Money;

class AdjustAccountPresenter extends BasePresenter implements AdjustAccountResponder {

    AdjustAccountPresenter(View view) {
        super(view);
    }

    @Override
    public void accept(AccountResponse response) {
        response.firstEntry().ifPresentOrElse(entry -> map(entry, response), this::onNotFound);
    }

    private void map(EntryResponse entry, AccountResponse response) {
        var viewModel = viewModelFrom(entry, response);

        responseOf(viewModel);
    }

    private AdjustAccountViewModel viewModelFrom(EntryResponse entry, AccountResponse response) {
        return new AdjustAccountViewModel(response.accountNumber(),
                                       formatted(response.balance()),
                                       response.balance().getCurrency().getCurrencyCode(),
                                       entry.transactionId(), formatted(entry.amount()));
    }

    private String formatted(Money amount) {
        return BasicMoneyFormatter.format(amount);
    }

}
