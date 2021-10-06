package com.crg.learn.controller.view;

public interface View {
    void render(Object viewModel);

    default void notFound() {
        // no-op
    };
}
