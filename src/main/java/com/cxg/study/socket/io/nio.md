### NIO - Non-blocking IO（非阻塞IO）
- #### Channels
> 这些通道涵盖了UDP 和 TCP 网络IO，以及文件IO。
> - FileChannel(文件IO)
> - DatagramChannel(UDP数据包)
> - SocketChannel(TCP客户端)
> - ServerSocketChannel(TCP服务端)
---
- #### Buffers -- 本质上是一块可以读写数据的内存；
[外链文档](http://ifeve.com/buffers/)
> 三个属性：
```
capacity -- 初始化指定，大小固定；
```
```
position -- 当你写数据到Buffer中时，position表示当前的位置。初始的position值为0.当一个byte、long等数据写到Buffer后， position会向前移动到下一个可插入数据的Buffer单元。position最大可为capacity – 1.
            当读取数据时，也是从某个特定位置读。当将Buffer从写模式切换到读模式，position会被重置为0. 当从Buffer的position处读取数据时，position向前移动到下一个可读的位置。
```
```
limit -- 在写模式下，Buffer的limit表示你最多能往Buffer里写多少数据。 写模式下，limit等于Buffer的capacity。
         当切换Buffer到读模式时， limit表示你最多能读到多少数据。因此，当切换Buffer到读模式时，limit会被设置成写模式下的position值。换句话说，你能读到之前写入的所有数据（limit被设置成已写数据的数量，这个值在写模式下就是position）
```
```
写数据到Buffer有两种方式：
1.从Channel写到Buffer。
2.通过Buffer的put()方法写到Buffer里。
--------------------------------
从Buffer中读取数据有两种方式：
1.从Buffer读取数据到Channel。
2.使用get()方法从Buffer中读取数据。
```
```
flip方法将Buffer从写模式切换到读模式。调用flip()方法会将position设回0，并将limit设置成之前position的值。
```
> 具体实现：
> - ByteBuffer
> - CharBuffer
> - DoubleBuffer
> - FloatBuffer
> - IntBuffer
> - LongBuffer
> - ShortBuffer
---
- #### Selectors
    - SelectionKey.OP_WRITE：表示底层缓冲区是否有空间，如果有则返回true；