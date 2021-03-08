### 从ActivityThread开始讲起

    class ActivityThread{
		public static void main(String[] args) {
		Looper.prepareMainLooper();//构造方法里创建MessageQueue(),采用ThreadLocal来存储当前线程的Looper
		
		Looper.loop();//开启消息循环,调用Queue的next()方法
	   }
    }

使用:
Handler handler=new Handler()
构造方法内部,获取当前线程的looper,也可采用带Looper的构造方法,直接传入Looper, sendMessage()会调用MessageQueue的enqueueMessage方法,把消息插入链表中,
Looper.loop是个死循环,这个死循环可以确保程序不会因为代码运行完毕而退出,同时不断取出消息进行处理, 没有消息时候采用C代码层的唤醒休眠机制,不会占用系统CPU

	class MessageQueue{
		Message next() {
			for (;;) {
		       nativePollOnce(ptr, nextPollTimeoutMillis);//阻塞调用native层的nativePollOnce方法来获取消息队列中的消息

			}
		}

		boolean enqueueMessage(Message msg, long when) {
			//将消息按时间顺序插入链表种的操作
			.....
			if (needWake) {//如果阻塞了,现在插入了新的消息就需要唤醒,
                		nativeWake(mPtr);//这一句会使nativePollOnce的阻塞状态解除,重新获取链表中的消息
            		}
			}
	}

ThreadLocal源码笔记:

使用:

```
ThreadLocal threadLocal=new ThreadLocal<String>(); 
String str="abc"
threadLocal.set(x)//一个ThreadLocal对象可以在不同的线程设置存储的值
String str=threadLocal.get()//不同线程get出来的值不一样
```  

get()方法:

```
public T get() {
        Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);//获取到当前线程的ThreadLocalMap
    if (map != null) {
        ThreadLocalMap.Entry e = map.getEntry(this);
        if (e != null) {
            @SuppressWarnings("unchecked")
            T result = (T)e.value;
            return result;
        }
    }
    return setInitialValue();
   }     
```

解析:
ThreadLocalMap是一个类似于HashMap的的数据结构,整体上以Entry数组存储 但是数组中没有存储链表的结构,只是单个ThreadLocal<?>的弱引用对象,实际上的方法调用链如下:

```
    threadLocal.get()-->Thread对象的ThreadLocalMap.getEntry(threadLocal)
```

这里ThreadLocal只是作为各个线程中threadLocals的key,存储的值作为Entry存储的value, 因为都是只作为当前线程的副本,以ThreadLocal为key,所以能在不同线程中存储不同的值




    
    





































