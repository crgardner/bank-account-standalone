package com.crg.learn.persistence.account;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.*;

@DynamoDBTable(tableName = "TestAccount")
public class PersistentAccount {

    private String holderFirstName;
    private String holderLastName;
    private String accountNumber;
    private List<PersistentEntry> entries = new ArrayList<>();

    @DynamoDBHashKey(attributeName = "accountNumber")
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @DynamoDBAttribute(attributeName = "holderFirstName")
    public String getHolderFirstName() {
        return holderFirstName;
    }

    public void setHolderFirstName(String holderFirstName) {
        this.holderFirstName = holderFirstName;
    }

    @DynamoDBAttribute(attributeName = "holderLastName")
    public String getHolderLastName() {
        return holderLastName;
    }

    public void setHolderLastName(String holderLastName) {
        this.holderLastName = holderLastName;
    }

    @DynamoDBAttribute(attributeName = "entries")
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
