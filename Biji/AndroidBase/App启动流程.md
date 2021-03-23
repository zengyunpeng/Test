### Activity启动流程 (基于sdk 30源码)
* 方法调用流程:
    Activity.startActivity()
--->Instrumentation.execStartActivity()
--->ActivityTaskManager.getService().startActivity()
--->最终调用的是这个对象的startActivity
  @UnsupportedAppUsage(trackingBug = 129726065)
  private static final Singleton<IActivityTaskManager> IActivityTaskManagerSingleton =
  new Singleton<IActivityTaskManager>() {
  @Override
  protected IActivityTaskManager create() {
  final IBinder b = ServiceManager.getService(Context.ACTIVITY_TASK_SERVICE);
  return IActivityTaskManager.Stub.asInterface(b);
  }
  };