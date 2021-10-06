package com.crg.learn.controller.presenter;

import com.crg.learn.controller.view.View;

public abstract class BasePresenter {
    private final View view;

    public BasePresenter(View view) {
        this.view = view;
    }

    public void onNotFound() {
        view.render("not found");
    }

    protected void responseOf(Object entity) {
        view.render(entity);
    }

}
