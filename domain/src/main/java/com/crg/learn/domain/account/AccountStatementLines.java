package com.crg.learn.domain.account;

import java.util.List;

class AccountStatementLines {
    private final List<AccountStatementLine> lines;

    AccountStatementLines(List<AccountStatementLine> lines) {
        this.lines = lines;
    }

    void export(AccountStatementExporter exporter) {
        lines.forEach(line -> line.export(exporter));
    }
}
