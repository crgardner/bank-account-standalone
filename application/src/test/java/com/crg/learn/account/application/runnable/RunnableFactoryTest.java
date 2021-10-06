package com.crg.learn.account.application.runnable;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("RunnableFactory")
class RunnableFactoryTest {

    private RunnableFactory factory;

    @BeforeEach
    void init() {
        factory = new RunnableFactory();
    }

    @ParameterizedTest
    @DisplayName("creates Runnable to execute UseCase")
    @ValueSource(strings = {
            "openAccount, First, Last",
            "adjustAccount, 123, 100, EUR",
            "prepareStatement, 123"
    })
    void createsRunnableToExecuteUseCase(String requestParts) {
        var command = factory.create(requestParts.split(","));

        assertThat(command).isNotNull();
    }

    @Test
    @DisplayName("throws exception when request is unknown")
    void throwExceptionWhenRequestIsUnknown() {
        assertThatThrownBy(() -> factory.create(new String[] { "unknown" }))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessageStartingWith("Unknown request");
    }
}