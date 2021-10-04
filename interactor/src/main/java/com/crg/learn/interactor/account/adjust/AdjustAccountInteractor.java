package com.crg.learn.interactor.account.adjust;

import com.crg.learn.domain.account.*;
import com.crg.learn.interactor.account.shared.AccountResponseBuilder;
import com.crg.learn.usecase.account.adjust.*;
import org.javamoney.moneta.Money;

import javax.money.Monetary;
import java.util.Optional;

public class AdjustAccountInteractor implements AdjustAccountUseCase {

    private final AccountRepository accountRepository;
    private final TransactionIdProvider transactionIdProvider;

    public AdjustAccountInteractor(AccountRepository accountRepository, TransactionIdProvider transactionIdProvider) {
        this.accountRepository = accountRepository;
        this.transactionIdProvider = transactionIdProvider;
    }

    @Override
    public void execute(AdjustAccountRequest request, AdjustAccountResponder responder) {
        var possibleAccount = findAccount(request);

        possibleAccount.ifPresentOrElse(account -> handle(account, request, responder), responder::onNotFound);
    }

    private Optional<Account> findAccount(AdjustAccountRequest request) {
        var accountNumber = new AccountNumber(request.accountNumber());

        return accountRepository.lookup(accountNumber);
    }

    private void handle(Account account, AdjustAccountRequest request, AdjustAccountResponder responder) {
        adjust(request, account);
        update(account);
        respond(responder, account);
    }

    private void adjust(AdjustAccountRequest request, Account account) {
        var transactionId = transactionIdProvider.nextTransactionId();
        var amount = Money.of(request.amount(), Monetary.getCurrency(request.currency()));
        var entry = new Entry(transactionId, amount);

        account.add(entry);
    }

    private void update(Account account) {
        accountRepository.update(account);
    }

    private void respond(AdjustAccountResponder responder, Account account) {
        var exporter = new AccountResponseBuilder();
        account.export(EntrySelectionRange.MOST_RECENT, exporter);

        responder.accept(exporter.build());
    }

}
