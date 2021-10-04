package com.crg.learn.interactor.account.statement;

import com.crg.learn.domain.account.*;
import com.crg.learn.usecase.account.statement.*;

import java.util.Optional;

public class PrepareAccountStatementInteractor implements PrepareAccountStatementUseCase {
    private final AccountRepository accountRepository;

    public PrepareAccountStatementInteractor(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void execute(PrepareAccountStatementRequest request, PrepareAccountStatementResponder responder) {
        var possibleAccount = findAccount(request);

        possibleAccount.ifPresentOrElse(account -> handle(account, responder), responder::onNotFound);
    }

    private Optional<Account> findAccount(PrepareAccountStatementRequest request) {
        return accountRepository.lookup(new AccountNumber(request.accountNumber()));
    }

    private void handle(Account account, PrepareAccountStatementResponder responder) {
        var statement = createStatementFrom(account);

        respond(responder, statement);
    }

    private AccountStatement createStatementFrom(Account account) {
        return account.createStatement();
    }

    private void respond(PrepareAccountStatementResponder responder, AccountStatement statement) {
        var exporter = new PrepareStatementResponseBuilder();
        statement.export(exporter);

        responder.accept(exporter.build());
    }

}
