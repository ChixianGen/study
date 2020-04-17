package com.cxg.study.utils.thirdf;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @Author: cxg
 * @Date: 2020/4/17 15:12
 * @Description: 布隆过滤器 .
 * @Copyright: All rights reserved.
 */
public class BloomCacheFilter {

    public static void main(String[] args) {
        int capacity = 1000000;
        int key = 6666;
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), capacity);
        for (int i = 0; i < capacity; i++) {
            bloomFilter.put(i);
        }
        /**返回计算机最精确的时间，单位纳妙 */
        long start = System.nanoTime();
        if (bloomFilter.mightContain(key)) {
            System.out.println("成功过滤到" + key);
        }
        long end = System.nanoTime();
        System.out.println("布隆过滤器消耗时间:" + (end - start));
    }
}
