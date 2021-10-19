package com.crg.learn.persistence.conversion;

import org.javamoney.moneta.Money;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.TypeConvertingVisitor;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.EnhancedAttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import javax.money.Monetary;

public class MoneyConverter implements AttributeConverter<Money> {


    @Override
    public AttributeValue transformFrom(Money money) {
        return AttributeValue.builder().s(convert(money)).build();
    }

    private String convert(Money money) {
        return String.format("%s:%s", money.getNumber(), money.getCurrency().getCurrencyCode());
    }

    @Override
    public Money transformTo(AttributeValue attributeValue) {
        try {
            if (attributeValue.s() != null) {
                return EnhancedAttributeValue.fromString(attributeValue.s()).convert(new Visitor());
            }
            return EnhancedAttributeValue.fromAttributeValue(attributeValue).convert(new Visitor());

        } catch (RuntimeException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public EnhancedType<Money> type() {
        return EnhancedType.of(Money.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }

    private static final class Visitor extends TypeConvertingVisitor<Money> {
        private Visitor() {
            super(Money.class);
        }

        @Override
        public Money convertString(String value) {
            var components = value.split(":");

            return Money.of(Double.parseDouble(components[0]), Monetary.getCurrency(components[1]));
        }

    }
}
