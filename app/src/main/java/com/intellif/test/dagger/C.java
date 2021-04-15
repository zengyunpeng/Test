package com.intellif.test.dagger;

import java.util.concurrent.atomic.AtomicBoolean;

public class C {

    public void test() {
//        Thread thread=new Thread();
//        thread.start();
//        thread.join();

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        if(atomicBoolean.compareAndSet(false,true)){

        }


    }
}
