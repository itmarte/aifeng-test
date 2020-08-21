package com.aifeng.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Description: tcp连接
 * @author: imart·deng
 * @date: 2020/5/20 17:03
 */
public class TestSocketChannel {

    public static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        Thread[] threadPool = new Thread[2];
        threadPool[0] = new Thread(()->{
            try {
                // 创建监听服务器
                createServer(PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        threadPool[0].start();
        threadPool[1] = new Thread(()->{
            try {
                // 创建监听服务器
                createClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        threadPool[1].start();
    }

    private static void createServer(int port) throws IOException {
        // 创建通道管理器 Selector
        Selector selector = Selector.open();

        // 获得一个ServerSocket通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        // 绑定端口
        serverChannel.socket().bind(new InetSocketAddress(port));
        // 如果一个 Channel 要注册到 Selector 中, 那么这个 Channel 必须是非阻塞的
        serverChannel.configureBlocking(false);

        // 将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件,注册该事件后，当该事件到达时，selector.select()会返回，如果该事件没到达selector.select()会一直阻塞。
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 监听
        while (true){
            //当注册的事件到达时，方法返回；否则,该方法会一直阻塞
            selector.select();

            // 获得selector中选中的项的迭代器，选中的项为注册的事件
            Iterator ite = selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = (SelectionKey) ite.next();
                // 删除已选的key,以防重复处理
                ite.remove();

                // 客户端请求连接事件
                if(key.isAcceptable()){
                    // do some thing
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    // 获得和客户端连接的通道
                    SocketChannel channel = server.accept();
                    // 设置成非阻塞
                    channel.configureBlocking(false);
                    //在这里可以给客户端发送信息哦
                    channel.write(ByteBuffer.wrap("向客户端发送了一条信息".getBytes()));
                    //在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限。
                    channel.register(selector, SelectionKey.OP_READ);
                }else if(key.isReadable()) { // 获得了可读的事件
                    // 服务器可读取消息:得到事件发生的Socket通道
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 创建读取的缓冲区
                    ByteBuffer buffer = ByteBuffer.allocate(10);
                    channel.read(buffer);
                    byte[] data = buffer.array();
                    String msg = new String(data).trim();
                    System.out.println("服务端收到信息："+msg);
                    ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
                    // 将消息回送给客户端
                    channel.write(outBuffer);
                }
            }
        }

    }

    private static void createClient() throws IOException {
        // 打开 SocketChannel
        SocketChannel channel = SocketChannel.open();

        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress("127.0.0.1", PORT));

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (!channel.finishConnect()) {
            // 发送数据
            String msg = "This is client.";
            // 在往 Buffer 中写数据之前，调用 clear()
            buffer.clear();
            buffer.put(msg.getBytes());

            // 在从 Buffer 中读数据之前，调用 flip()
            buffer.flip();
            channel.write(buffer);

            // 接收数据
            // 在往 Buffer 中写数据之前，调用 clear()
            buffer.clear();

            // 调用 channel 的 read 方法往 Buffer 中写数据
            channel.read(buffer);

            // 在从 Buffer 中读数据之前，调用 flip()
            buffer.flip();

            // 从 Buffer 中读数据
            while (buffer.hasRemaining()) {
                System.out.print(buffer.get());
            }
        }
    }
}
