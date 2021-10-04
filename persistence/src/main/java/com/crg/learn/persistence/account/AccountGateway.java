package com.crg.learn.persistence.account;

import com.crg.learn.domain.account.*;

import java.util.Optional;

public class AccountGateway implements AccountRepository {

    @Override
    public void open(Account account) {
        update(account);
    }

    @Override
    public void update(Account account) {
        var mapper = new PersistentAccountMapper(account);
        var persistentAccount = mapper.map();

    }

    @Override
    public Optional<Account> lookup(AccountNumber accountNumber) {
        return null;
//        return possiblePersistentAccount.map(this::toAccount);
    }

    private Account toAccount(PersistentAccount persistentAccount) {
        return new Account(new AccountMapper(persistentAccount));
    }

}
