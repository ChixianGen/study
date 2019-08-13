总结：
1：对于map/set的选择使用
// 不考虑并发问题
HashMap
TreeMap
LinkedHashMap

Hashtable
Collections.sychronizedXXX

// 高并发的情况考虑使用的容器：
ConcurrentHashMap               -- 无序
ConcurrentSkipListMap（跳表）    -- 有序
ConcurrentSkipListSet

2：队列
ArrayList
LinkedList
Collections.synchronizedXXX
CopyOnWriteList
Queue
	CocurrentLinkedQueue //concurrentArrayQueue
	BlockingQueue // 阻塞式队列
		LinkedBQ
		ArrayBQ
		TransferQueue
		SynchronusQueue
	DelayQueue执行定时任务
		
	