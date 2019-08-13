package com.cxg.study.socket.base.tcp;   // Administrator 于 2019/8/1 创建;

import java.io.*;
import java.net.*;

public class SimpleServerDemo {

    private static final int IP_PORT = 8888;

    public static void main(String[] args) throws IOException, InterruptedException {
//        baseTest();
        baseTest1();
    }

    public static void baseTest1() throws IOException {
        ServerSocket serverSocket = new ServerSocket(IP_PORT);
        // 阻塞式等待；一次accept就相当于一个客户端连接；
        Socket client = serverSocket.accept();
        System.out.printf("客户端地址：【%s】\n", client.getInetAddress().getHostAddress());
        DataInputStream dis = new DataInputStream(client.getInputStream());
        String string = dis.readUTF();
        System.out.printf("========服务器接收到的数据：【%s】============\n", string);

        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeUTF("服务端响应的数据：" + string);
        dos.writeBoolean(true);
        dos.flush();
        dis.close();
        dos.close();

        // 释放资源
        client.close();
        serverSocket.close();
    }

    private static void baseTest() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(IP_PORT);
        // 阻塞式等待；一次accept就是一个客户端连接；
        Socket client = serverSocket.accept();
        System.out.printf("客户端地址：【%s】\n", client.getInetAddress().getHostAddress());
        InputStream inputStream = client.getInputStream();
        byte[] bytes = new byte[1024];
        int len = inputStream.read(bytes);
        String string = new String(bytes, 0, len);
        System.out.printf("========服务器接收到的数据：【%s】============\n", string);
        Thread.sleep(2000);

        OutputStream outputStream = client.getOutputStream();
        outputStream.write(("服务端相应：" + string).getBytes());
        outputStream.flush();
        outputStream.close();
        inputStream.close();

        // 释放资源
        client.close();
        serverSocket.close();
    }

}
