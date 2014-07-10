package com.cliffyuan.nio;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.*;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * Created by yuanyuanming on 14-7-10.
 */
public class NioServer {
    public static void main(String[] args)throws IOException{
        block();
        //nonblock();
    }

    /**
     * 默认为阻塞模式
     * @throws IOException
     */
    public static void block()throws IOException{
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        SocketChannel socketChannel=null;
        while (true){
            System.out.println("accept start");
            socketChannel=serverSocketChannel.accept();
            if(socketChannel!=null){
                System.out.println("socketChannel="+socketChannel);
                System.out.println("accept end,");
                break;
            }
        }
        ByteBuffer byteBuffer= ByteBuffer.allocate(64);
        System.out.println("读取数据开始");
        int count=socketChannel.read(byteBuffer);
        System.out.println("读取数据结束，共多少字节:"+count+",数据为:"+byteBuffer.getInt());
    }

    /**
     * 非阻塞模式
     * @throws IOException
     */
    public static void nonblock()throws IOException{
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        SocketChannel socketChannel=null;
        while (true){
            System.out.println("accept start");
            socketChannel=serverSocketChannel.accept();
            if(socketChannel!=null){
                System.out.println("socketChannel="+socketChannel);
                System.out.println("accept end,");
                break;
            }
        }

        ByteBuffer byteBuffer= ByteBuffer.allocate(64);
        System.out.println("读取数据开始");
        int count=socketChannel.read(byteBuffer);
        System.out.println("读取数据结束，共多少字节:"+count+",数据为:"+byteBuffer.getInt());
    }

}
