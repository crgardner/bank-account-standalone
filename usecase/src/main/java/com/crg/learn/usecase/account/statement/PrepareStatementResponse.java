package com.crg.learn.usecase.account.statement;

import java.util.List;

public record PrepareStatementResponse(List<PrepareStatementResponseLine> lines) {
}
