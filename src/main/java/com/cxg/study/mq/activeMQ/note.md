### 引入消息中间件（mq）的目的
1. 解决系统之间的耦合调用问题；                   -- 解耦
2. 异步数据处理（异步模型）；                     -- 异步
3. 流量削峰，保护主业务的目的（流量缓存池）；      -- 削峰
***
### 引入消息中间件的弊端：
1.
***


### 队列消息消费的方式
- 同步阻塞(receive())
- 异步非阻塞（监听回调onMessage()）

#### Queue 
> - 如果消息已生产且未被消费，会一直存在，等待消费者消费；

#### Topic
> - 如果没有订阅（消费）者，那么发布的消息就是废消息；所以应该先要有主题的订阅者；
> - 消息有时效性，也就是说消费者只能消费订阅后的消息，无法消费订阅之前生产者发布的消息；
---

### Topic-发布/订阅模式队列 与 Queue-负载均衡模式队列的区别
- 工作模式：
    - Topic：如果没有订阅者，那么发布的消息会被丢弃；如果有多个消费者，那么这些消费者都会接收到同样的消息；
    - Queue：没有消费者，消息不会被丢弃；如果有多个消费者，消息也只会分发给其中一个消费者；并且要求消费者ack（应答）信息（为了确保消息是否送达）；
- 有无状态：
    - Topic：无状态；
    - Queue：数据在mq服务器上默认以文件形式保存，也可以配置成DB存储；
- 传递的完整性：
    - Topic：没有订阅者，消息废弃；
    - Queue：消息不废弃；
- 处理效率：
    - Topic：随着订阅者越多，性能会降低；
    - Queue：由于消息只需要发送给一个消费者，所以消费者再多，性能也不会有太明显的下降；
    
---
### JMS 组成结构
- JMS provider：各种MQ服务器；
- JMS producer：消息生产者；
- JMS consumer：消息消费者；
- JMS message：消息对象；
    - 消息头：消息的有效期，权重，目的地，是否持久化，messageID等信息（可编辑）。
    - 消息体：
        - TextMessage：JAVA String类型的数据；
        - MapMessage：map类型数据；k-v
        - BytesMessage：二进制数组数据；
        - StreamMessage：流数据；
        - ObjectMessage：可序列化的JAVA对象；
    - 消息属性：（属性key-属性value键值对）开发人员自定义的，主要用于***识别/去重/重点标注***等操作；
---
### JMS 可靠性
- 持久化：持久化了的消息，如果MQ服务器宕机恢复后，未消费的消息仍然存在；
    ```
        MessageProducer producer = session.createProducer(queue);
        // 可以手动设置持久化策略（默认开启持久化）；
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
    ```
    ```
        源码如下：
        protected ActiveMQMessageProducer(ActiveMQSession session, ProducerId producerId, ActiveMQDestination destination, int sendTimeout) throws JMSException {
                super(session);
                ...
                ...
                // 此处默认设置为持久化；
                this.defaultDeliveryMode = 2;
                
                this.defaultPriority = 4;
                this.defaultTimeToLive = 0L;
                this.startTime = System.currentTimeMillis();
                this.messageSequence = new AtomicLong(0L);
                this.stats = new JMSProducerStatsImpl(session.getSessionStats(), destination);
                ...
                ...
            }
    ```
    > 发布订阅主题模式的持久化（默认开启持久化）：
    > - 先运行消费者，等于向MQ服务器注册自己，类似于订阅某个主题；
    > - 然后运行生产者发送消息；
    > - 如果消费者在线，会立即接收到消息；消费者不在线的话，下次连接上线时就会接收到离线之后没有接收到的消息；
- 事务
    > - 批处理消息，保证了消息成功与否的一致性；
    > - 事务偏生产者/签收偏消费者；
- 消息签收
    - 非事务情况：
        - 自动签收（默认）：
        ```
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        ```
        - 手动签收：
         ```
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        
        ...
        ...
        
        message.acknowledge(); // 手动签收；
        ```
        - 允许重复消费消息（较少使用）：
        ```
        Session session = connection.createSession(false, Session.DUPS_OK_ACKNOWLEDGE);
        ```
    - 开启事务情况：
    > 开启事务后，签收模式无效了，因为默认就是 0；
    ```
        源码如下：
        public Session createSession(boolean transacted, int acknowledgeMode) throws JMSException {
            this.checkClosedOrFailed();
            this.ensureConnectionInfoSent();
            if (!transacted) {
                ...
                ...
            }
            return new ActiveMQSession(this, this.getNextSessionId(), transacted ? 0 : acknowledgeMode, this.isDispatchAsync(), this.isAlwaysSessionAsync());
        }
    ```
> #### 事务和签收的关系：
> - 在事务性会话中，当一个事务被成功提交则消息被自动签收；如果事务回滚，则消息会被再次传送；
> - 在非事务性会话中，消息何时被确认取决于创建session会话时设置的应答模式；

### JMS的发布/订阅总结
- 非持久订阅
    > 只有当某个主题的消费者（客户端）存在并与MQ服务器保持正常连接状态时，该主题的生产者生产的消息才会被消费；否则消息会被作废；
- 持久化订阅
    > 消费者（客户端）先向MQ服务器注册自己，当客户端离线（宕机）后，MQ服务器会为这个客户端保留所有的订阅消息；当客户端恢复连接时，会收到所有在离线期间生产者发布的订阅消息；
> - 非持久状态下，不能恢复或重新发送一个未签收的消息；
> - 持久订阅才能恢复或重新发送一个未签收的消息；
> - 如果所有消息都必须被消费，需要持久订阅；如果容忍丢失消息，则可以使用非持久订阅；

### activeMQ的传输协议
- TCP：默认协议；
- NIO：基于TCP之上，进行了扩展和优化，有更好的扩展性；
> 以下协议不常用
- UDP
- SSL
- HTTP(S)

