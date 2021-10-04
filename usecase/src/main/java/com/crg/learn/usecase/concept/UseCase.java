package com.crg.learn.usecase.concept;

public interface UseCase<I, O> {
    void execute(I request, O responder);
}
