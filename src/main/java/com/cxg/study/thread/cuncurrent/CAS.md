### CAS - 比较和交换（Conmpare And Swap）是用于实现多线程同步的原子指令
>  它将内存位置的内容与给定值进行比较，只有在相同的情况下，将该内存位置的内容修改为新的给定值。 这是作为单个原子操作完成的（CPU硬件保证的原子操作）。 
原子性保证新值基于最新信息计算; 如果该值在同一时间被另一个线程更新，则写入将失败。 

---
#### 引发的问题
- ABA问题
> - 问题描述：线程1从内存X中取出A，这时候另一个线程2也从内存X中取出A，并且线程2进行了一些操作将内存X中的值变成了B，
然后线程2又将内存X中的数据变成A，这时候线程1进行CAS操作发现内存X中仍然是A，然后线程1操作成功。
虽然线程1的CAS操作成功，但是整个过程就是有问题的。
比如链表的头在变化了两次后恢复了原值，但是不代表链表就没有变化。
>
> - 解决方案：在对象中额外再增加一个标记来标识对象是否有过变更；（类似于使用版本号更新）
>    - AtomicStampedReference
>    - AtomicMarkableReference

- 