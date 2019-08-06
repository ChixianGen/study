package com.cxg.study.socket.base.udp;   // Administrator 于 2019/8/2 创建;

import com.cxg.study.utils.IOUtil;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.LocalDateTime;

public class UdpServerDemo {

    private static final int SERVER_IP_PORT = 9999;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("udp服务器等待接收数据。。。");
        DatagramSocket serverSocket = new DatagramSocket(SERVER_IP_PORT);

        // 接收数据的容器
        byte[] container = new byte[1024];
        DatagramPacket packet = new DatagramPacket(container, 0, container.length);
        serverSocket.receive(packet);

        // 接收到的数据正文；
        byte[] content = packet.getData();
//        baseTest(content);
//        baseTest1(content);
//        baseTest2(content);
        baseTest3(content);
        serverSocket.close();
    }

    /**
     * 操作图片
     * @param content
     */
    private static void baseTest3(byte[] content) {
        String newPath = "C:\\Users\\Administrator\\Pictures\\icon\\back.jpg";
        IOUtil.byteArray2File(content, newPath);
        System.out.println("========图片传输完成=========");
    }

    /**
     * 操作引用类型；
     * @param content
     */
    private static void baseTest2(byte[] content) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(content)));
        // 读取的顺序要与发送的数据包顺序相同；（严格处理应该加上类型判断 instanceof ）
        LocalDateTime o = (LocalDateTime) ois.readObject();
        StringBuilder o1 = (StringBuilder) ois.readObject();
        Person o2 = (Person) ois.readObject();
        System.out.printf("接收到的引用类型数据：【%s】，【%s】，【%s】；", o, o1, o2);
    }

    /**
     * 操作基本类型
     * @param content
     * @throws IOException
     */
    private static void baseTest1(byte[] content) throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(content)));
        // 读取的顺序要与发送的数据包顺序相同，不然可能会导致解析错误；
        String str = dis.readUTF();
        boolean b = dis.readBoolean();
        char c = dis.readChar();
        double v = dis.readDouble();
        System.out.printf("服务端接收到的数据：bytes:[%s]----char:[%s]----boolean:[%s]-----double:[%s]\n", str, c, b, v);
    }

    private static void baseTest(byte[] content) throws IOException {
        System.out.printf("server接收到的数据：%s", new String(content, 0, content.length));
    }
}
