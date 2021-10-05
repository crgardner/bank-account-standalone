package com.crg.learn.persistence.conversion;

import org.junit.jupiter.api.*;

import java.time.*;

import static org.assertj.core.api.Assertions.*;

@DisplayName("InstantConverter")
class InstantConverterTest {

    private Instant jan_01_2021;
    private InstantConverter converter;
    private String instantString;

    @BeforeEach
    void init() {
        jan_01_2021 = LocalDateTime.of(2021, 1, 18, 3, 30).toInstant(ZoneOffset.UTC);
        instantString = "2021-01-18T03:30:00Z";

        converter = new InstantConverter();
    }

    @Test
    @DisplayName("converts Instant to String")
    void convertsInstantToString() {
        var result = converter.convert(jan_01_2021);

        assertThat(result).isEqualTo(instantString);
    }

    @Test
    @DisplayName("converts String to Instant")
    void convertsStringToInstant() {
        var result = converter.unconvert(instantString);

        assertThat(result).isEqualTo(jan_01_2021);
    }
}