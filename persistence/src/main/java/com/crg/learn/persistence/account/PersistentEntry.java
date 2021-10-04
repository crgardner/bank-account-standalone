package com.crg.learn.persistence.account;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.crg.learn.persistence.conversion.*;
import org.javamoney.moneta.Money;

import java.time.Instant;
import java.util.StringJoiner;

@DynamoDBDocument
public class PersistentEntry {
    private Instant whenBooked;
    private Money amount;
    private String transactionId;

    @DynamoDBAttribute(attributeName = "whenBooked")
    @DynamoDBTypeConverted(converter = InstantConverter.class)
    public Instant getWhenBooked() {
        return whenBooked;
    }

    public void setWhenBooked(Instant whenBooked) {
        this.whenBooked = whenBooked;
    }

    @DynamoDBAttribute(attributeName = "amount")
    @DynamoDBTypeConverted(converter = MoneyConverter.class)
    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    @DynamoDBAttribute(attributeName = "transactionId")
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PersistentEntry.class.getSimpleName() + "[", "]").add("whenBooked=" + whenBooked)
                .add("amount=" + amount).add("transactionId='" + transactionId + "'").toString();
    }
}
