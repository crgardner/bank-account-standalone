package com.crg.learn.account.application.config;

import com.crg.learn.domain.account.*;
import com.crg.learn.interactor.account.adjust.AdjustAccountInteractor;
import com.crg.learn.interactor.account.open.OpenAccountInteractor;
import com.crg.learn.interactor.account.statement.PrepareAccountStatementInteractor;
import com.crg.learn.usecase.account.adjust.AdjustAccountUseCase;
import com.crg.learn.usecase.account.open.OpenAccountUseCase;
import com.crg.learn.usecase.account.statement.PrepareAccountStatementUseCase;

public class InteractorConfiguration {

    public OpenAccountUseCase openAccountUseCase(AccountRepository accountRepository) {
        return new OpenAccountInteractor(accountRepository, new UUIDAccountNumberProvider());
    }

    public AdjustAccountUseCase adjustAccountUseCase(AccountRepository accountRepository) {
        return new AdjustAccountInteractor(accountRepository, new UUIDTransactionIdProvider());
    }

    public PrepareAccountStatementUseCase prepareAccountStatementUseCase(AccountRepository accountRepository) {
        return new PrepareAccountStatementInteractor(accountRepository);
    }

}
