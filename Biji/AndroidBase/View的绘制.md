### View的绘制流程
布局的填充:
Activity.setContentView(layoutResID)-->getWindow().setContentView(layoutResID); -->PhoneWindow.setContentView(
layoutResID)

```
  @Override
    public void setContentView(int layoutResID) {
        // Note: FEATURE_CONTENT_TRANSITIONS may be set in the process of installing the window
        // decor, when theme attributes and the like are crystalized. Do not check the feature
        // before this happens.
        if (mContentParent == null) {   //当mContentParent为空的时候,表示当前内容还未放置到窗口,也就是第一次调用的时候
            installDecor(); //  创建并添加DecorView
        } else if (!hasFeature(FEATURE_CONTENT_TRANSITIONS)) {  //如果内容已经加载过(不是第一次加载)，并且不需要动画,removeAllViews()
            mContentParent.removeAllViews();
        }

        if (hasFeature(FEATURE_CONTENT_TRANSITIONS)) {
            final Scene newScene = Scene.getSceneForLayout(mContentParent, layoutResID,getContext());
            transitionTo(newScene); //添加完Content后如有设置了FEATURE_CONTENT_TRANSITIONS则添加Scene来过度启动
        } else {
            //把我们设置的view添加到视图树种
            mLayoutInflater.inflate(layoutResID, mContentParent); 
        }
        mContentParent.requestApplyInsets();
        final Callback cb = getCallback();
        if (cb != null && !isDestroyed()) {
            cb.onContentChanged();
        }
    }
```
上面的步骤只是将xml文件解析成控件树,并没有开始渲染
View的绘制,先从ActivityThread的handleResumeActivity()方法:
```
public void handleResumeActivity(IBinder token, boolean finalStateRequest, boolean isForward,
    ....
    ViewManager wm = a.getWindowManager();
    View decor = r.window.getDecorView();
    WindowManager.LayoutParams l = r.window.getAttributes();
    wm.addView(decor, l);

    ....
}

```
上面的wm是WindowManagerImpl的对象,

WindowManagerImpl的addView()方法
```
    public void addView(@NonNull View view, @NonNull ViewGroup.LayoutParams params) {
        applyDefaultToken(params);
        mGlobal.addView(view, params, mContext.getDisplayNoVerify(), mParentWindow,
                mContext.getUserId());
    }
```
转到WindowManagerGlobal的addView方法
```
  public void addView(View view, ViewGroup.LayoutParams params,
            Display display, Window parentWindow, int userId) {
            ....
               root.setView(view, wparams, panelParentView, userId);
            ....
            }
```
ViewRootImpl
```
public void setView(View view, WindowManager.LayoutParams attrs, View panelParentView,
            int userId) {
        requestLayout();  //完成异步刷新请求，重绘界面
        
 }

    public void requestLayout() {
        if (!mHandlingLayoutInLayoutRequest) {
            checkThread();
            mLayoutRequested = true;
            scheduleTraversals();
        }
    }
    
        @UnsupportedAppUsage
    void scheduleTraversals() {
        if (!mTraversalScheduled) {
            mTraversalScheduled = true;
            mTraversalBarrier = mHandler.getLooper().getQueue().postSyncBarrier();
            //主要绘制流程在mTraversalRunnable里
            mChoreographer.postCallback(
                    Choreographer.CALLBACK_TRAVERSAL, mTraversalRunnable, null);
            notifyRendererOfFramePending();
            pokeDrawLockIfNeeded();
        }
    }
    
```
mTraversalRunnable对应的类TraversalRunnable
```
final class TraversalRunnable implements Runnable {
        @Override
        public void run() {
            doTraversal();
        }
    }
    
        void doTraversal() {
        if (mTraversalScheduled) {
            mTraversalScheduled = false;
            mHandler.getLooper().getQueue().removeSyncBarrier(mTraversalBarrier);

            if (mProfile) {
                Debug.startMethodTracing("ViewAncestor");
            }
            //主要绘制流程在这个方法
            performTraversals();

            if (mProfile) {
                Debug.stopMethodTracing();
                mProfile = false;
            }
        }
    }
    
```
performTraversals绘制流程的主要实现:

```
public void performTraversals(){
    ....
     // 1.测量
     performMeasure(childWidthMeasureSpec, childHeightMeasureSpec);
     // 2.排列好View              
     performLayout(lp, mWidth, mHeight);
      //3.绘制View
      performDraw();
}

   private void performMeasure(int childWidthMeasureSpec, int childHeightMeasureSpec) {
        if (mView == null) {
            return;
        }
        Trace.traceBegin(Trace.TRACE_TAG_VIEW, "measure");
        try {
        
            //mView就是传入的DecorView,这里进入View的遍历测量流程
            mView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        } finally {
            Trace.traceEnd(Trace.TRACE_TAG_VIEW);
        }
    }
    
    
    private void performLayout(WindowManager.LayoutParams lp, int desiredWindowWidth,
            int desiredWindowHeight) {
             final View host = mView;
        try {
            //这里开启测量流程
            host.layout(0, 0, host.getMeasuredWidth(), host.getMeasuredHeight());
            }finally {
            Trace.traceEnd(Trace.TRACE_TAG_VIEW);
        }
            
    }
     private void performDraw() {
         ... 
         //draw(boolean)-->drawSoftware()-->mView.draw(canvas)  到这一不久进入View的绘制流程
         boolean canUseAsync = draw(fullRedrawNeeded);
         ..
     }

```
#### View的绘制只描述父子View之间的绘制流程

1.测量 onMeasure(int widthMeasureSpec, int heightMeasureSpec)
这个方法负责子控件的测量和自身最终宽高的确定

以LinearLayout为例说明测量过程
```
    //这个方法的两个参数,是父控件的大小+测量模式的 32位int值
    //其中测量模式是由父控件和子空间的布局参数共同决定的
    //测量是个递归的过程,并且起始点在ViewRootImpl中,最开始传入的是窗体的大小和布局参数值作出来的测量规格
    //从父类开始测量,先测量完子View再把计算出来的值设置给当前View对应setMeasuredDimension方法
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mOrientation == VERTICAL) {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        }
    }
    
    
    



```




2.布局 onLayout(boolean changed, int l, int t, int r, int b)
//先设置父布局的位置,再根据父布局的位置设置子布局的位置
和测量步骤稍有不同
//onLayout方法只负责子控件的摆放

3.绘制 onDraw(Canvas canvas)
这里onDraw方法,只负责自己内容的绘制
```
public void draw(Canvas canvas) {
final int privateFlags = mPrivateFlags;
        mPrivateFlags = (privateFlags & ~PFLAG_DIRTY_MASK) | PFLAG_DRAWN;

        /*
         * Draw traversal performs several drawing steps which must be executed
         * in the appropriate order:
         *
         *      1. Draw the background
         *      2. If necessary, save the canvas' layers to prepare for fading
         *      3. Draw view's content
         *      4. Draw children
         *      5. If necessary, draw the fading edges and restore layers
         *      6. Draw decorations (scrollbars for instance)
         *      7. If necessary, draw the default focus highlight
         */

        // Step 1, draw the background, if needed
        int saveCount;

        drawBackground(canvas);

// skip step 2 & 5 if possible (common case)
final int viewFlags = mViewFlags;
        boolean horizontalEdges = (viewFlags & FADING_EDGE_HORIZONTAL) != 0;
        boolean verticalEdges = (viewFlags & FADING_EDGE_VERTICAL) != 0;
        if (!verticalEdges && !horizontalEdges){
        // Step 3, draw the content
        onDraw(canvas);

        // Step 4, draw the children
        dispatchDraw(canvas);
        ....
        }
```





常见问题:
1.getWidth和getMeasureWidth的区别:前者返回right-left 后者返回mMeasuredWidth,前者再布局时赋值,后者测量时赋值,一般相等,或者后者更大





















