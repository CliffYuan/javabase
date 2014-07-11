package com.cliffyuan.io;

import java.io.IOError;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.SocketChannel;

/**
 * 建立远程连接
 * 注意点：
 * （1）必须设置flip()，否则数据无法写入
 *
 *
 *
 * Created by yuanyuanming on 14-7-10.
 */
public class NioClient {
    public static void main(String[] args)throws IOException,InterruptedException{
        SocketChannel socketChannel=SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
        System.out.println("连接情况：" + socketChannel.finishConnect()
                + "，绑定远程端口：" + socketChannel.getRemoteAddress()
                + "，绑定本地端口：" + socketChannel.getLocalAddress());
        Thread.sleep(10000);
        System.out.println("睡眠2s结束");
        ByteBuffer byteBuffer=ByteBuffer.allocate(64);

        byteBuffer.putInt(234567);
        byteBuffer.putLong(1223L);

        //设置很重要，否则数据无法写入
        byteBuffer.flip();
        int count= socketChannel.write(byteBuffer);

        System.out.println("向服务端发送数据大小："+count+"字节");
    }
}
