package com.cxg.study.socket.base.tcp;   // Administrator 于 2019/8/1 创建;

import java.io.*;
import java.net.Socket;

public class SimpleClientDemo {

    private static final String IP_ADDR = "192.168.0.14";
    private static final int IP_PORT = 8888;

    public static void main(String[] args) throws IOException {
//        InetAddress address = InetAddress.getByName("www.shsxt.com");
//        System.out.println(address.getHostAddress());
//        baseTest();
        baseTest1();
    }

    private static void baseTest1() throws IOException {
        Socket socket = new Socket(IP_ADDR, IP_PORT);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("hello server");
        dos.flush();

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String s = dis.readUTF();
        boolean b = dis.readBoolean();
        System.out.printf("客户端接收到的返回数据：【%s】,【%s】；\n", s, b);
        dos.close();
        dis.close();
        socket.close();
    }

    private static void baseTest() throws IOException {
        Socket socket = new Socket(IP_ADDR, IP_PORT);

        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello server CXG".getBytes());

        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = inputStream.read(bytes);
        System.out.printf("客户端接收到的返回数据：【%s】\n", new String(bytes, 0, len));

        outputStream.flush();
        outputStream.close();
        socket.close();
    }
}
