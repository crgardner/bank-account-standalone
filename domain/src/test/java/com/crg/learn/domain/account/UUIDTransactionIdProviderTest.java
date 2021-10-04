package com.crg.learn.domain.account;

import org.junit.jupiter.api.*;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

@DisplayName("UUIDAccountNumberProvider")
class UUIDTransactionIdProviderTest {
    private final static Pattern UUID_REGEX_PATTERN = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Test
    @DisplayName("provides transaction ID in UUID format")
    void providesTransactionIdInUUIDFormat() {
        var provider = new UUIDTransactionIdProvider();

        var transactionId = provider.nextTransactionId();

        assertThat(transactionId.value()).matches(UUID_REGEX_PATTERN);
    }

}