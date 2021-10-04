package com.crg.learn.usecase.account.adjust;


import com.crg.learn.usecase.shared.AccountResponse;

public interface AdjustAccountResponder {
    void accept(AccountResponse response);

    void onNotFound();

}

