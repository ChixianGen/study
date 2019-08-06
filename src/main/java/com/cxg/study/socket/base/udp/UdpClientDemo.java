package com.cxg.study.socket.base.udp;   // Administrator 于 2019/8/2 创建;

import com.cxg.study.utils.IOUtil;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.Arrays;

public class UdpClientDemo {

    private static final String IP_ADDR = "192.168.0.14";
    private static final int IP_PORT = 8888;
    private static final int SERVER_IP_PORT = 9999;

    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();

//        byte[] data = baseTest();
//        byte[] data = baseTest1();
//        byte[] data = baseTest2();
        byte[] data = baseTest3();

        DatagramPacket packet = new DatagramPacket(data, data.length, new InetSocketAddress(IP_ADDR, SERVER_IP_PORT));
        datagramSocket.send(packet);
        datagramSocket.close();
        System.out.printf("=======client数据发送成功：【%s】=========", Arrays.toString(data));
    }

    /**
     * 文件对象
     *
     * @return
     */
    private static byte[] baseTest3() {
        String logo = "C:\\Users\\Administrator\\Pictures\\icon\\logo.jpg";
        return IOUtil.file2ByteArray(logo);
    }

    /**
     * 操作引用类型（对象）；
     *
     * @return
     */
    private static byte[] baseTest2() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(baos));
        oos.writeObject(LocalDateTime.now());
        oos.writeObject(new StringBuilder("字符串对象"));
        oos.writeObject(new Person(19, "池贤根", 8888));
        oos.flush();
        return baos.toByteArray();
    }

    /**
     * 操作基本类型
     *
     * @return
     * @throws IOException
     */
    private static byte[] baseTest1() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(baos));
        dos.writeUTF("hello world");
        dos.writeBoolean(true);
        dos.writeChar(78);
        dos.writeDouble(58.88);
        dos.flush();
        return baos.toByteArray();
    }

    private static byte[] baseTest() throws IOException {
        return "FANGJIALA".getBytes();
    }
}
