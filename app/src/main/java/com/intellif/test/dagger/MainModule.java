package com.intellif.test.dagger;

import dagger.Module;
import dagger.Provides;

import javax.inject.Named;

/**
 * 模块依赖的其他模块
 */
@Module(includes = BModule.class)
public class MainModule {
    @Provides
    A providerA(@Named("dev") B b) {
        return new A(b);
    }

    //named标签用于标识虽然是同类型,实例化对象不同
    @Named("dev")
    @Provides
    B providerB() {
        return new B();
    }

    @Named("proDuct")
    @Provides
    B providerProDuctB() {
        return new B();
    }

}
