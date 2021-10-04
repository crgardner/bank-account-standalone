package com.crg.learn.interactor.account.open;

import com.crg.learn.domain.account.*;
import com.crg.learn.domain.person.Person;
import com.crg.learn.interactor.account.shared.AccountResponseBuilder;
import com.crg.learn.usecase.account.open.*;

public class OpenAccountInteractor implements OpenAccountUseCase {
    private final AccountRepository accountRepository;
    private final AccountNumberProvider accountNumberProvider;

    public OpenAccountInteractor(AccountRepository accountRepository, AccountNumberProvider accountNumberProvider) {
        this.accountRepository = accountRepository;
        this.accountNumberProvider = accountNumberProvider;
    }

    public void execute(OpenAccountRequest request, OpenAccountResponder responder) {
        var account = createAccountFrom(request);
        open(account);
        respond(responder, account);
    }

    private Account createAccountFrom(OpenAccountRequest request) {
        var accountNumber = accountNumberProvider.nextAccountNumber();
        var accountHolder = new Person(request.firstName(), request.lastName());

        return new Account(accountNumber, accountHolder);
    }

    private void open(Account account) {
        accountRepository.open(account);
    }

    private void respond(OpenAccountResponder responder, Account account) {
        var exporter = new AccountResponseBuilder();

        account.export(exporter);
        responder.accept(exporter.build());
    }

}
