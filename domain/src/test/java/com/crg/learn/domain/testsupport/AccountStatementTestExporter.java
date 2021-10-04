package com.crg.learn.domain.testsupport;

import com.crg.learn.domain.account.AccountStatementExporter;
import org.javamoney.moneta.Money;

import java.time.Instant;
import java.util.*;

public class AccountStatementTestExporter implements AccountStatementExporter {
    private final List<AccountStatementEntryExporter> statementEntries = new ArrayList<>();

    @Override
    public void addLine(Instant whenBooked, Money amount, Money balance) {
        statementEntries.add(new AccountStatementEntryExporter(whenBooked, amount, balance));
    }

    public List<AccountStatementEntryExporter> statementEntries() {
        return statementEntries;
    }


}
