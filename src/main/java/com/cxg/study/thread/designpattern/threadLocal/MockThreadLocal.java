package com.cxg.study.thread.designpattern.threadLocal;   // Administrator 于 2019/8/15 创建;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟 java.lang.ThreadLocal 对象
 */
@Data
@RequiredArgsConstructor
public class MockThreadLocal<T> {
    @NonNull
    String name;

    public T get(MockThread current) {
//        MockThread current = MockThread.current();
//        System.out.println("当前线程：" + current);
        MockThreadLocalMap map = current.getThreadLocalMap();
        return (T) map.get(this);
    }

    public void put(MockThread current, T value) {
        MockThreadLocalMap map = current.getThreadLocalMap();
        map.put(this, value);
    }

    public MockThreadLocalMap newMockThreadLocalMap() {
        return new MockThreadLocalMap();
    }

    static class MockThreadLocalMap<T> {
        Map map = new HashMap<>();

        public T get(MockThreadLocal<T> key) {
            return (T) map.get(key);
        }

        public void put(MockThreadLocal<T> key, T value) {
            map.put(key, value);
        }

        @Override
        public String toString() {
            return "MockThreadLocalMap{" +
                    "map=" + map +
                    ", hashcode='" + this.hashCode() + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MockThreadLocal{" +
                "name='" + name + '\'' +
                ", hashcode='" + this.hashCode() + '\'' +
                '}';
    }
}
