package com.crg.learn.controller.account.statement;

import com.crg.learn.controller.account.shared.BasicMoneyFormatter;
import com.crg.learn.controller.presenter.BasePresenter;
import com.crg.learn.controller.view.View;
import com.crg.learn.usecase.account.statement.*;

import static java.util.stream.Collectors.*;

class PrepareAccountStatementPresenter extends BasePresenter implements PrepareAccountStatementResponder {

    PrepareAccountStatementPresenter(View view) {
        super(view);
    }

    @Override
    public void accept(PrepareStatementResponse response) {
        var viewModel = viewModelFrom(response);

        responseOf(viewModel);
    }

    private StatementViewModel viewModelFrom(PrepareStatementResponse response) {
        return response.lines().stream().map(this::toLine)
                                        .collect(collectingAndThen(toList(), StatementViewModel::new));
    }

    private Line toLine(PrepareStatementResponseLine responseLine) {
        return new Line(responseLine.whenBooked().toString(),
                        debitOrCredit(responseLine),
                        BasicMoneyFormatter.formatAbs(responseLine.amount()),
                        BasicMoneyFormatter.format(responseLine.balance())
        );
    }

    private String debitOrCredit(PrepareStatementResponseLine responseLine) {
        return responseLine.amount().isNegative() ? "DEBIT" : "CREDIT";
    }


}
