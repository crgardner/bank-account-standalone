package com.crg.learn.persistence.conversion;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;

import javax.money.Monetary;

@Disabled
@DisplayName("MoneyAttributeConverter")
class MoneyConverterTest {

    private MoneyConverter converter;
    private Money money;

    @BeforeEach
    void init() {
        converter = new MoneyConverter();
        money = Money.of(100.25, Monetary.getCurrency("EUR"));
    }

    @Test
    @DisplayName("converts money to string")
    void convertsMoneyToString() {
//        var string = converter.convert(money);

//        assertThat(string).isEqualTo("100.25:EUR");
    }

    @Test
    @DisplayName("converts string to money")
    void convertsStringToMoney() {
//        var convertedMoney = converter.unconvert("100.25:EUR");
//
//        assertThat(convertedMoney).isEqualTo(money);
    }
}