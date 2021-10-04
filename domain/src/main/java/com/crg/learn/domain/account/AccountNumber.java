package com.crg.learn.domain.account;

import java.util.Objects;

public class AccountNumber {

    private final String value;

    public AccountNumber(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountNumber accountNumber))  {
            return false;
        }

        return value.equals(accountNumber.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String value() {
        return value;
    }

}
