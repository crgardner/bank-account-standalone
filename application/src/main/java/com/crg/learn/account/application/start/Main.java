package com.crg.learn.account.application.start;

import com.crg.learn.account.application.config.*;
import com.crg.learn.controller.account.adjust.AdjustAccountDetails;
import com.crg.learn.controller.account.open.OpenAccountDetails;

public class Main {
    private static final ControllerConfiguration controllerConfiguration = new ControllerConfiguration();
    private static final InteractorConfiguration interactorConfiguration = new InteractorConfiguration();
    private static final PersistenceConfiguration persistenceConfiguration = new PersistenceConfiguration();

    public static void main(String[] args) {

        var accountGateway = persistenceConfiguration.accountGateway();

        if ("openAccount".equals(args[0])) {
            var openAccountUseCase = interactorConfiguration.openAccountUseCase(accountGateway);
            var openAccountController = controllerConfiguration.openAccountController(openAccountUseCase);

            openAccountController.openAccount(new OpenAccountDetails(args[1], args[2]));

            return;
        }

        if ("adjustAccount".equals(args[0])) {
            var adjustAccountUseCase = interactorConfiguration.adjustAccountUseCase(accountGateway);
            var adjustAccountController = controllerConfiguration.adjustAccountController(adjustAccountUseCase);

            adjustAccountController.adjustAccount(new AdjustAccountDetails(args[1], args[2], args[3]));

            return;

        }

        if ("prepareStatement".equals(args[0])) {
            var prepareAccountStatementUseCase = interactorConfiguration.prepareAccountStatementUseCase(accountGateway);
            var prepareStatementController = controllerConfiguration.prepareStatementController(prepareAccountStatementUseCase);

            prepareStatementController.prepareStatement(args[1]);
        }

    }

}
