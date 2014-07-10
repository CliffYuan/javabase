package com.cliffyuan.nio;

import java.io.IOError;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.SocketChannel;

/**
 * Created by yuanyuanming on 14-7-10.
 */
public class NioClient {
    public static void main(String[] args)throws IOException{
        SocketChannel socketChannel=SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
        System.out.println(socketChannel.finishConnect() + "--" + socketChannel.getRemoteAddress() + "--" + socketChannel.getLocalAddress());
        ByteBuffer byteBuffer=ByteBuffer.allocate(64);
        byteBuffer.putInt(234567);
       int count= socketChannel.write(byteBuffer);
        System.out.println("向服务端发送数据"+count);
    }
}
