package com.crg.learn.controller.test.support;

import com.crg.learn.usecase.concept.UseCase;
import org.mockito.invocation.InvocationOnMock;

import java.util.function.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public final class UseCaseMocking {
    private UseCaseMocking() {
        // default
    }

    public static <I, O> void prepare(UseCase<I, O> useCase, BiConsumer<I, O> actionToPerform) {
        doAnswer(useCaseInvocation -> {
            I request = requestFrom(useCaseInvocation);
            O responder = responderFrom(useCaseInvocation);

            actionToPerform.accept(request, responder);
            return null;
        }).when(useCase).execute(any(), any());
    }

    public static <I, O> void prepare(UseCase<I, O> useCase, Consumer<O> actionToPerform) {
        doAnswer(useCaseInvocation -> {
            O responder = responderFrom(useCaseInvocation);

            actionToPerform.accept(responder);
            return null;
        }).when(useCase).execute(any(), any());
    }

    private static <I> I requestFrom(InvocationOnMock invocation) {
        return invocation.getArgument(0);
    }

    private static <O> O responderFrom(InvocationOnMock invocation) {
        return invocation.getArgument(1);
    }
}
