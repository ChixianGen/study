package com.cxg.study.socket.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 基于轮询的非阻塞IO
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress("127.0.0.1", 8888));
        ssc.configureBlocking(false);

        System.out.println("server started, listening on :" + ssc.getLocalAddress());
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int select = selector.select();
            if (select == 0) continue;
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();
                handle(key);
            }
        }

    }

    private static void handle(SelectionKey key) {
        if (key.isAcceptable()) {
            System.out.println("=============isAcceptable==============");
            try {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(key.selector(), SelectionKey.OP_READ);
            } catch (IOException e) {
            } finally {
            }
        } else if (key.isReadable()) { //flip
            System.out.println("=============isReadable==============");
            try {
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(512);
                buffer.clear();
                int len = sc.read(buffer);
                String string = null;
                if (len != -1) {
                    string = new String(buffer.array(), 0, len);
                    System.out.printf("客户端请求的消息：【%s】\n", string);
                    ByteBuffer bufferToWrite = ByteBuffer.wrap("返回给客户端的数据".getBytes());
                    sc.write(bufferToWrite);
                } else {
                    System.out.println("客户端关闭了");
                    key.cancel(); // 防止客户端关闭后出现死循环；
                }
            } catch (IOException e) {
            }
        }
    }
}
