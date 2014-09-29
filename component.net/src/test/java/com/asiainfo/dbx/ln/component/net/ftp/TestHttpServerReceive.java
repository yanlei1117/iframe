package com.asiainfo.dbx.ln.component.net.ftp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by yanlei on 2014/9/3.
 */
public class TestHttpServerReceive {

    public  TestHttpServerReceive(int port){
        this.port = port;
    }
    int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private Logger logger = LoggerFactory.getLogger(TestHttpServerReceive.class);
    public void start(){
        try{
            startToReceiveHttpRequest();
        }catch (Throwable e){
            logger.error("",e);
        }
    }
    public void startToReceiveHttpRequest() throws Exception{
        ByteBuffer readBuffer = ByteBuffer.allocate(1024*1024);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();//打开通道
        serverSocketChannel.socket().bind(new InetSocketAddress(this.getPort()));//绑定到指定端口
        serverSocketChannel.configureBlocking(false);//配置为非阻塞模式
        Selector selector = Selector.open();//打开选择器
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//注册，让选择器监控可接受连接事件
        logger.info("打开HTTP端口：{}", this.getPort());
        while (true) {
            selector.select();
            Set<SelectionKey> keySet = selector.selectedKeys();//获取发生事件的键集
            Iterator<SelectionKey> iterator = keySet.iterator();
            while (iterator.hasNext()) {//遍历键集
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {//发生可接受连接事件，
                    SocketChannel sc = serverSocketChannel.accept();//接受socket连接 ，ServerSocketChannel只支持SelectionKey.OP_ACCEPT
                    sc.configureBlocking(false);//设置非阻塞模式sc.configureBlocking(false);//设置非阻塞模式
                    sc.register(selector,SelectionKey.OP_READ);
                }else if(key.isReadable()){
                    SocketChannel sc = (SocketChannel)key.channel();
                    readBuffer.clear();

                    int readNum =  sc.read(readBuffer);

                    logger.info("readNum:{}",readNum);
                    readBuffer.flip();
                    byte [] data = new byte [readBuffer.remaining()];
                    readBuffer.get(data);

                    logger.info("\n{}",new String (data));
                    sc.write(ByteBuffer.wrap(responseOKString().getBytes()));
                    key.cancel();
                    sc.close();
                }
                iterator.remove();
            }
        }
    }
    private String responseOKString(){
        return "HTTP/1.1 200 OK\n" +
                "Server: Apache-Coyote/1.1\n" +
                "Set-Cookie: JSESSIONID=BA9D174136CD6C087C474CC511F359FA; Path=/; HttpOnly\n" +
                "Content-Type: text/html;charset=UTF-8\n" +
                "Date: Wed, 03 Sep 2014 03:33:58 GMT\n" +
                "Content-Length: 0\n\n";
    }
    public static void main(String [] args){
        new TestHttpServerReceive(80).start();
    }
}
