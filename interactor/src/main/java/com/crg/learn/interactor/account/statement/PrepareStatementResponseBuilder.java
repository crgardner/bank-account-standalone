package com.crg.learn.interactor.account.statement;

import com.crg.learn.domain.account.AccountStatementExporter;
import com.crg.learn.usecase.account.statement.*;
import org.javamoney.moneta.Money;

import java.time.Instant;
import java.util.*;

class PrepareStatementResponseBuilder implements AccountStatementExporter {
    private final List<PrepareStatementResponseLine> lines = new ArrayList<>();

    @Override
    public void addLine(Instant whenBooked, Money amount, Money balance) {
        lines.add(new PrepareStatementResponseLine(whenBooked, amount, balance));
    }

    PrepareStatementResponse build() {
        return new PrepareStatementResponse(Collections.unmodifiableList(lines));
    }

}
