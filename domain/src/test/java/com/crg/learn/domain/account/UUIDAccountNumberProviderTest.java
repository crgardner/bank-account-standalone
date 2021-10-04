package com.crg.learn.domain.account;

import org.junit.jupiter.api.*;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

@DisplayName("UUIDAccountNumberProvider")
class UUIDAccountNumberProviderTest {
    private final static Pattern UUID_REGEX_PATTERN = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Test
    @DisplayName("provides account number in UUID format")
    void providesAccountNumberInUUIDFormat() {
        var provider = new UUIDAccountNumberProvider();

        var accountNumber = provider.nextAccountNumber();

        assertThat(accountNumber.value()).matches(UUID_REGEX_PATTERN);
    }

}