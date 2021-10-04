package com.crg.learn.usecase.account.open;

import com.crg.learn.usecase.shared.AccountResponse;

public interface OpenAccountResponder {
    void accept(AccountResponse response);
}
