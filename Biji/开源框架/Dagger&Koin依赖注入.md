#### Dagger 
* 使用
> https://www.jianshu.com/p/2cd491f0da01
>>@Inject：需要注入依赖的地方，Dagger 会构造一个该类的实例并满足它所需要的依赖；
>>@Module：依赖的提供者，Module 类中的方法专门提供依赖，并用 @Provides 注解标记；
>>@Component：依赖的注入者，是 @Inject 和 @Module 的桥梁，它从 @Module 中获取依赖并注入给 @Inject
* 原理

