package com.cxg.study.thread.singleton;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: cxg
 * @Date: 2020/4/17 15:16
 * @Description: 枚举类型的单例模式 .
 * @Copyright: All rights reserved.
 */
@Getter
public enum Singleton3 implements Worker {
    SINGLETON_3;

    private String name;
    private Integer price;

    public String custom(String str) {
        return name + ":" + StringUtils.join(str.split(" "), "-") + ":" + price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String read() {
        return "枚举单例";
    }
}

interface Worker {
    String read();
}

@Slf4j
class SingleTest {
    public static void main(String[] args) {
        Singleton3 a = Singleton3.SINGLETON_3;
        a.setName("cxg");
        a.setPrice(123);
        log.debug("实例对象{} - 读取 - {}", a, a.read());
        log.debug("结果 - {}", a.custom("hello world"));
    }
}
