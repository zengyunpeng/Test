package com.intellif.test.dagger;

import dagger.Module;

@Module
public class BModule {
    C getC() {
        return new C();
    }
}
