package com.crg.learn.persistence.conversion;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import org.javamoney.moneta.Money;

import javax.money.Monetary;

public class MoneyConverter implements DynamoDBTypeConverter<String, Money> {
    @Override
    public String convert(Money money) {
        return String.format("%s:%s", money.getNumber(), money.getCurrency().getCurrencyCode());
    }

    @Override
    public Money unconvert(String dbData) {
        var components = dbData.split(":");

        return Money.of(Double.parseDouble(components[0]), Monetary.getCurrency(components[1]));
    }
}
