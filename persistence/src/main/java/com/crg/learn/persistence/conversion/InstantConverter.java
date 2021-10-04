package com.crg.learn.persistence.conversion;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class InstantConverter implements DynamoDBTypeConverter<String, Instant> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_INSTANT;

    @Override
    public String convert(Instant instant) {
        return instant == null ? null : DATE_TIME_FORMATTER.format(instant);
    }

    @Override
    public Instant unconvert(String str) {
        return str == null ? null : Instant.from(DATE_TIME_FORMATTER.parse(str));
    }
}