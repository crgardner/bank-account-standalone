package com.crg.learn.persistence.account;

import com.amazonaws.services.dynamodbv2.datamodeling.AbstractDynamoDBMapper;
import com.crg.learn.domain.account.*;

import java.util.Optional;

public class AccountGateway implements AccountRepository {

    private final AbstractDynamoDBMapper dynamoDb;

    public AccountGateway(AbstractDynamoDBMapper dynamoDb) {
        this.dynamoDb = dynamoDb;
    }

    @Override
    public void open(Account account) {
        update(account);
    }

    @Override
    public void update(Account account) {
        var mapper = new PersistentAccountMapper(account);
        var persistentAccount = mapper.map();

        dynamoDb.save(persistentAccount);
    }

    @Override
    public Optional<Account> lookup(AccountNumber accountNumber) {
        var possiblePersistentAccount = Optional.ofNullable(
                dynamoDb.load(PersistentAccount.class, accountNumber.value()));

        return possiblePersistentAccount.map(this::toAccount);
    }

    private Account toAccount(PersistentAccount persistentAccount) {
        return new Account(new AccountMapper(persistentAccount));
    }

}
