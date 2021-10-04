package com.crg.learn.domain.account;

public class AccountStatement {
    private final AccountStatementLines statementLines;

    public AccountStatement(AccountStatementLines statementLines) {
        this.statementLines = statementLines;
    }

    public void export(AccountStatementExporter exporter) {
        statementLines.export(exporter);
    }
}
