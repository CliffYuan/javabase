package com.cliffyuan.io;

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
 * 绑定端口
 * 注意：
 *
 * （0）必须设置flip()，否则读取得的数据无法使用
 * （1）设置serverSocketChannel的阻塞模式，只对serverSocketChannel.accept()方法起作用。
 * （2）设置socketChannel.configureBlocking(false);只对socketChannel.read()和wirte（）方法起作用
 *
 *
 *
 *
 * Created by yuanyuanming on 14-7-10.
 */
public class NioServer {
    public static void main(String[] args)throws IOException{
        //acceptBlock();
        //acceptNonblock();
        acceptNonblockReadNonblock();
    }

    /**
     * 默认为阻塞模式
     * @throws IOException
     */
    public static void acceptBlock()throws IOException{
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
        long start=System.currentTimeMillis();
        int count=socketChannel.read(byteBuffer);
        byteBuffer.flip();
        System.out.println("读取数据结束，共多少字节:"+count+",数据为:"+byteBuffer.getInt()+","+byteBuffer.getLong()+",耗时:"+(System.currentTimeMillis()-start));
    }

    /**
     * 非阻塞模式
     * @throws IOException
     */
    public static void acceptNonblock()throws IOException{
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
        long start=System.currentTimeMillis();
        int count=socketChannel.read(byteBuffer);
        byteBuffer.flip();
        System.out.println("读取数据结束，共多少字节:"+count+",数据为:"+byteBuffer.getInt()+",耗时:"+(System.currentTimeMillis()-start));
    }

    /**
     * 非阻塞模式
     * @throws IOException
     */
    public static void acceptNonblockReadNonblock()throws IOException{
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

        socketChannel.configureBlocking(false);

        ByteBuffer byteBuffer= ByteBuffer.allocate(64);
        System.out.println("读取数据开始");
        long start=System.currentTimeMillis();
        int count=socketChannel.read(byteBuffer);
        if(count>0){
            byteBuffer.flip();
            System.out.println("读取数据结束，共多少字节:"+count+",数据为:"+byteBuffer.getInt()+",耗时:"+(System.currentTimeMillis()-start));
        }else {
            System.out.println("读取数据结束，共多少字节:"+count+",无数据,耗时:"+(System.currentTimeMillis()-start));
        }
    }

}
