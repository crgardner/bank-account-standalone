package com.crg.learn.persistence.account;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.util.*;

@DynamoDbBean
public class PersistentAccount {
    public static final String TABLE_NAME = "BankAccount";

    private String holderFirstName;
    private String holderLastName;
    private String accountNumber;
    private List<PersistentEntry> entries = new ArrayList<>();

    @DynamoDbPartitionKey
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getHolderFirstName() {
        return holderFirstName;
    }

    public void setHolderFirstName(String holderFirstName) {
        this.holderFirstName = holderFirstName;
    }

    public String getHolderLastName() {
        return holderLastName;
    }

    public void setHolderLastName(String holderLastName) {
        this.holderLastName = holderLastName;
    }

    public List<PersistentEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<PersistentEntry> entries) {
        this.entries = entries;
    }

    public void add(PersistentEntry entry) {
        entries.add(entry);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PersistentAccount.class.getSimpleName() + "[", "]").add(
                        "holderFirstName='" + holderFirstName + "'").add("holderLastName='" + holderLastName + "'")
                .add("accountNumber='" + accountNumber + "'").add("entries=" + entries).toString();
    }
}
