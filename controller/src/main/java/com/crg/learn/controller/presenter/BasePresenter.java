package com.crg.learn.controller.presenter;

public abstract class BasePresenter {
    private Object entity;

    public void onNotFound() {
        entity = "not found";
    }

    public Object responseEntity() {
        return entity;
    }

    protected void responseOf(Object entity) {
        this.entity = entity;
    }
}
