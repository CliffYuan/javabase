#### 回收永久代
1. 回收类(判断一个类是否是无用的类，需满足3个条件)：
    a. 该类的实例都已经被回收，java堆中不存在该类的任何实例。
    b. 加载该类的ClassLoader已经被回收。
    c. 该类对应的Class对象没有被任何地方引用，无法在任何地方通过反射访问该类的方法。

2. 回收常量（跟堆上回收对象类似）


#### 内存分配

##### 对象进入年老代的情况

1. 当对象大于-XX:PretenureSizeThreshold设置的大小时，对象直接分配在年老代。  
2. 当对象年龄达到-XX:MaxTenuringThreshold设置的阀值，对象进入年老代。  
3. 如果Survivor空间中相同年龄所有对象大小的总和大于Survivor空间的一半，年龄大于或者等于该年龄的对象就可以直接进入年老代。  




#### Minor GC和Full GC
在发生Minor GC时候，虚拟机会检测之前每次晋升到老年代的平均大小是否大于老年代的剩余空间大小，  
1)如果大于，则改为直接进行一次Full GC。  
2)如果小于，则查看HandlePromotionFailure设置是否容许担保失败；  
    a.如果容许，只会进行Minor GC; 在Minor GC后，进入年老代对象突增，担保失败后，还是会进行一次Full GC。    
    b.如果不容许，则也改为进行一次Full GC;  
    