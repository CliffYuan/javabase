## 收集算法

### 标记-清除算法（Mark-Sweep）  
算法分为"标记"和"清除"两个阶段：首先标记出所有需要回收的对象，在标记完成后统一回收掉所有被标记的对象。  
存在两个问题：  
1.效率问题  
2.空间问题（产生大量不连续的碎片）  

### 复制算法
主要用来回收新生代。
新生代中的对象98%是朝三夕死的。所以不是1:1的比例来划分内存空间，默认比例：    
Eden:Survivor1:Survivor2=8:1:1  
只有Eden和其中一个Survivor中被使用。只有10%的空间被浪费。  
当复制到Survivor中的另一个时，但空间不足够时，需要老年代来分配担保（Handle Promotion）,即这些对象直接通过分配担保机制进入老年代。  

### 标记-整理算法
主要用于老年代


分代收集算法，指将堆分为新生代和老年代，根据各代的特点使用不同的收集算法。  

## 垃圾收集器
### Yong generation(年轻代)  
* Serial  
  
* ParNew （复制算法，Serial的多线程版本）  
  -XX:+UseParNewGC 启用这个收集器  
  -XX:ParallelGCThreads 设置垃圾收集线程数  
    
* Parallel Scavenge（复制算法，跟ParNew类似，但关注吞吐量的收集器）    
  -XX:MaxGCPauseMillis 设置最大垃圾收集停顿时间（毫秒）    
  -XX:GCTimeRatio 设置吞吐量大小0<x<100    
  -XX:+UseAdaptiveSizePolicy 开起GC自适应调节策略（不需要设置新生代大小[-Xmn],Eden和Survivor比例[-XX:SurvivorRatio]，晋升老年代对象年龄[-XX:PretenureSizeThreshold]）。
  
* G1  

### Tenured generation（年老代）
* Serial Old(标记-整理算法)  

* Parallel Old (Parallel Scavenge的老年代版本)


* CMS  
  -XX:+UseConcMarkSweepGC,当设置这个时，新生代默认使用ParNew.
  -XX:+UseCMSCompactAtFullCollection 在Full GC后进行一次碎片整理。
  -XX:CMSFullGCsBeforeCompaction 进行x次不压缩的Full GC后，进行一次压缩。
  -XX:CMSInitiatingOccupancyFarction 触发CMS收集器的老年代内存占用比
  回收过程：  
  初始标记  
  并发标记  
  重新标记  
  并发清除  
  
  3个缺点：  
  1).对CPU资源非常敏感，线程数默认为(cpu+3)/4 
  2).无法处理浮动垃圾，  
  3).空间碎片    
  
  CMS收集器当老年代使用了68%的空间后就会被激发，可以通过CMSInitiatingOccupancyFarction参数进行设置。

* G1

