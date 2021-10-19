package com.crg.learn.persistence.account;

import com.crg.learn.persistence.conversion.MoneyConverter;
import org.javamoney.moneta.Money;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.Instant;
import java.util.StringJoiner;

@DynamoDbBean
public class PersistentEntry {
    private Instant whenBooked;
    private Money amount;
    private String transactionId;

    public Instant getWhenBooked() {
        return whenBooked;
    }

    public void setWhenBooked(Instant whenBooked) {
        this.whenBooked = whenBooked;
    }

    @DynamoDbConvertedBy(MoneyConverter.class)
    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

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
