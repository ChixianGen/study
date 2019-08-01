package com.cxg.study.mq;   // Administrator 于 2019/7/31 创建;

import com.cxg.study.mq.activeMQ.springboot.ActivemqBootApplication;
import com.cxg.study.mq.activeMQ.springboot.ApplicationContextProvider;
import com.cxg.study.mq.activeMQ.springboot.config.ConfigBean;
import com.cxg.study.mq.activeMQ.springboot.produce.QueueProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ActivemqBootApplication.class)
public class ActivemqTests {

    @Autowired
    private QueueProducer queueProducer;

    /**
     * 测试包扫描；
     */
//    @Test
    public void testPackageScan() {
        ConfigBean bean = ApplicationContextProvider.getBean(ConfigBean.class);
        Optional.of("队列名称：" + bean.getMyqueue()).ifPresent(System.out::println);

        // TestDemo.class 不在 ActivemqBootApplication.class类路径下，扫描不到
//        TestDemo bean1 = ApplicationContextProvider.getBean(TestDemo.class);
//        Optional.of("TestDemo：" + bean1.getName()).ifPresent(System.out::println);
    }

    @Test
    public void sendMsg() {
//        for (int i = 0; i < 10; i++) {
            queueProducer.produceMsg();
//        }
    }

}
