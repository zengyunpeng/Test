#### 触摸反馈

view的触摸事件:

```
    public boolean dispatchTouchEvent(MotionEvent event){
        boolean result=false;
        
        //....鼠标触摸事件检查
        //....mOnTouchListener触摸事件检查
        //onTouchEvent()方法处理事件
        if (!result && onTouchEvent(event)) {
                result = true;
        }
        
    }
   
```

viewGroup的触摸事件关键代码:

```
public boolean dispatchTouchEvent(MotionEvent ev) {
    //....触摸事件安全性检测 略过
    
    // Handle an initial down.重置触摸事件,清楚原来的各种触摸事件状态,参考英文意义很明确
    if (actionMasked == MotionEvent.ACTION_DOWN) {
                // Throw away all previous state when starting a new touch gesture.
                // The framework may have dropped the up or cancel event for the previous gesture
                // due to an app switch, ANR, or some other state change.
          cancelAndClearTouchTargets(ev);
          resetTouchState();
   }
   
   //1. 检查是否拦截当前事件
   final boolean intercepted;
   if (actionMasked == MotionEvent.ACTION_DOWN
              || mFirstTouchTarget != null) {//当为down事件或者之前的事件有消费的子View的时候,去判断是否拦截
          ////mGroupFlags这个标志位对应ViewGroup的requestDisallowInterceptTouchEvent方法
          // 即请求不要拦截该触摸事件,一般在子View中getParent.requestDisallowInterceptTouchEvent()调用
          final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
          if (!disallowIntercept) {
              //允许拦截,调用自身的onInterceptTouchEvent
              intercepted = onInterceptTouchEvent(ev);
              ev.setAction(action); // restore action in case it was changed
          } else {
              intercepted = false;
          }
          
    else {
                //不是down事件且没有view处理这个事件,当前view直接拦截事件,无需向下分发
                // There are no touch targets and this action is not an initial down
                // so this view group continues to intercept touches.
                intercepted = true;
   }
   
   //2. 如果事件没有拦截或者取消,并且是down事件就寻找处理这个事件的View
   if (!canceled && !intercepted) {
             if (actionMasked == MotionEvent.ACTION_DOWN
                        || (split && actionMasked == MotionEvent.ACTION_POINTER_DOWN)
                        || actionMasked == MotionEvent.ACTION_HOVER_MOVE) {
                        
                        ....
                        //查找触摸事件的处理View
                        
                        }
   
   
   }
   
   // Dispatch to touch targets.
   if (mFirstTouchTarget == null) {
           // No touch targets so treat this as an ordinary view.
           handled = dispatchTransformedTouchEvent(ev, canceled, null,
           TouchTarget.ALL_POINTER_IDS);
   } else {
        //
          TouchTarget predecessor = null;
                TouchTarget target = mFirstTouchTarget;
                while (target != null) {
                    final TouchTarget next = target.next;
                    //alreadyDispatchedToNewTouchTarget这个变量用于标记事件是否派发过了
                    //如果事件已经派发过并且遍历出的View和新派发的View相等直接返回true
                    //可以理解为down事件上面肯定是派发过了,这里主要也是处理down事件,down事件上面派发过了
                    //这里返回true就ok
                    if (alreadyDispatchedToNewTouchTarget && target == newTouchTarget) {
                        handled = true;
                    } else {
                        //这里主要派发move和up事件
                        final boolean cancelChild = resetCancelNextUpFlag(target.child)
                                || intercepted;
                        //把事件派发给子View
                        if (dispatchTransformedTouchEvent(ev, cancelChild,
                                target.child, target.pointerIdBits)) {
                            handled = true;
                        }
                        if (cancelChild) {
                            if (predecessor == null) {
                                mFirstTouchTarget = next;
                            } else {
                                predecessor.next = next;
                            }
                            target.recycle();
                            target = next;
                            continue;
                        }
                    }
                    predecessor = target;
                    target = next;
                }
    }
    
    //....非关键代码,其他情况的处理
    
    return handled;

}


```

