package com.crg.learn.account.application.view;

import com.crg.learn.controller.view.View;

public class SystemOutView implements View {

    @Override
    public void render(Object viewModel) {
        System.out.println(viewModel);
    }
}
