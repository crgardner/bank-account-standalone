package com.crg.learn.account.application.start;

import com.crg.learn.account.application.runnable.RunnableFactory;

public class Main {
    private static final RunnableFactory runnableFactory = new RunnableFactory();

    public static void main(String[] args) {
        var runnable = runnableFactory.create(args);

        runnable.run();
    }

}
