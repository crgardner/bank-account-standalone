package com.crg.learn.account.application.runnable;

import com.crg.learn.account.application.config.*;
import com.crg.learn.controller.account.adjust.AdjustAccountDetails;
import com.crg.learn.controller.account.open.OpenAccountDetails;

import java.util.*;
import java.util.function.*;

public class RunnableFactory {
    private final ControllerConfiguration controllers = new ControllerConfiguration();
    private final InteractorConfiguration interactors = new InteractorConfiguration();
    private final PersistenceConfiguration persistence = new PersistenceConfiguration();

    private final Map<String, Function<String[], Runnable>> commandFactories = new HashMap<>(Map.of(
        "openAccount", this::openAccount,
        "adjustAccount", this::adjustAccount,
        "prepareStatement", this::prepareStatement
    ));

    public Runnable create(String[] requestParts) {
        return commandFactories.computeIfAbsent(requestParts[0],
                                                    r -> { throw new IllegalArgumentException("Unknown request"); }
                                                ).apply(requestParts);
    }

    private Runnable openAccount(String[] requestParts) {
        if (requestParts.length != 3) {
            throw new IllegalArgumentException("openAccount requires first name and last name");
        }

        var openAccountUseCase = interactors.openAccountUseCase(persistence.accountGateway());
        var openAccountController = controllers.openAccountController(openAccountUseCase);

        return () -> openAccountController.openAccount(new OpenAccountDetails(requestParts[1], requestParts[2]));
    }

    private Runnable adjustAccount(String[] requestParts) {
        if (requestParts.length != 4) {
            throw new IllegalArgumentException("adjustAccount requires account number, amount, and currency");
        }

        var adjustAccountUseCase = interactors.adjustAccountUseCase(persistence.accountGateway());
        var adjustAccountController = controllers.adjustAccountController(adjustAccountUseCase);

        return () ->  adjustAccountController.adjustAccount(new AdjustAccountDetails(requestParts[1], requestParts[2],
                                                                                     requestParts[3]));
    }

    private Runnable prepareStatement(String[] requestParts) {
        if (requestParts.length != 2) {
            throw new IllegalArgumentException("prepareStatement requires account number");
        }

        var prepareAccountStatementUseCase = interactors.prepareAccountStatementUseCase(persistence.accountGateway());
        var prepareStatementController = controllers.prepareStatementController(prepareAccountStatementUseCase);

        return () -> prepareStatementController.prepareStatement(requestParts[1]);
    }
}
