package com.cxg.study.classloader;   // Administrator 于 2019/7/26 创建;

import java.nio.charset.Charset;

public class SimpleEncrypt {

    private static final byte DEFAULE = (byte) 0xff;
    private static String TEST = "Hello world";

    public static void main(String[] args) {
//        test1();
        byte[] bytes = new byte[] {88, 56, 27};
        System.out.println(new String(bytes, Charset.defaultCharset()));
    }

    private static void test1() {
        byte[] origin = TEST.getBytes();
        char[] bytes = new char[origin.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (char) (origin[i] ^ DEFAULE);
        }
        System.out.println(new String(bytes));

        char[] nc = new char[origin.length];
        for (int i = 0; i < bytes.length; i++) {
            nc[i] = (char) (bytes[i] ^ DEFAULE);
        }
        System.out.println(new String(nc));
    }
}
