package com.crg.learn.usecase.account.statement;

public interface PrepareAccountStatementResponder {
    void accept(PrepareStatementResponse response);

    void onNotFound();
}
