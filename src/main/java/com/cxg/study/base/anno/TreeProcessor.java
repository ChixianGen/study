package com.cxg.study.base.anno;

import java.lang.reflect.Method;

/**
 * @Author: cxg
 * @Date: 2019/10/27 17:08
 * @Copyright: All rights reserved.
 */
public class TreeProcessor<T> {

    public void parseMethod(final Class<T> clazz) throws Exception {
        T t = clazz.newInstance();
        final Method[] methods = clazz.getDeclaredMethods();
        for (final Method method : methods) {
            System.out.println(method.isAnnotationPresent(Tree.class));
            final Tree tree = method.getAnnotation(Tree.class);
            if (null != tree) {
                method.invoke(t, tree.value());
            }
        }
    }
}
