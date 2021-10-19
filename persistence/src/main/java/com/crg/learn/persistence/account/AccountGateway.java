package com.crg.learn.persistence.account;

import com.crg.learn.domain.account.*;
import software.amazon.awssdk.enhanced.dynamodb.*;

import java.util.Optional;

public class AccountGateway implements AccountRepository {

    private final DynamoDbTable<PersistentAccount> bankAccountTable;

    public AccountGateway(DynamoDbTable<PersistentAccount> bankAccountTable) {
        this.bankAccountTable = bankAccountTable;
    }

    @Override
    public void open(Account account) {
        var mapper = new PersistentAccountMapper(account);
        var persistentAccount = mapper.map();

        bankAccountTable.putItem(persistentAccount);
    }

    @Override
    public void update(Account account) {
        var mapper = new PersistentAccountMapper(account);
        var persistentAccount = mapper.map();

        bankAccountTable.updateItem(persistentAccount);
    }

    @Override
    public Optional<Account> lookup(AccountNumber accountNumber) {
        var persistentAccount = bankAccountTable.getItem(Key.builder().partitionValue(accountNumber.value()).build());
        var possiblePersistentAccount = Optional.ofNullable(persistentAccount);

        return possiblePersistentAccount.map(this::toAccount);
    }

    private Account toAccount(PersistentAccount persistentAccount) {
        return new Account(new AccountMapper(persistentAccount));
    }

}
