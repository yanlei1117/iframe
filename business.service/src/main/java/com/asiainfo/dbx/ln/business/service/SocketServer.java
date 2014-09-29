package com.asiainfo.dbx.ln.business.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by yanlei on 2014/8/19.
 */
public class SocketServer extends Thread{
    private Logger logger = LoggerFactory.getLogger(SocketServer.class);
    int port = 0;

    public SocketServer(){
        this.setDaemon(true);
    }
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    volatile  boolean runFlag  = false;
     public void stopPort(){
         runFlag = false;
     }

    public void startPort(){
        runFlag= true;
        if(!startThread){
            this.start();
            startThread = !startThread;
        }
    }
    boolean startThread = false;
    @Override
    public void run() {
        try {

            while (true) {
                 if (runFlag) {
                     ServerSocketChannel serverSocketChannel= null;
                    try {
                        serverSocketChannel = ServerSocketChannel.open();//打开通道
                        serverSocketChannel.socket().bind(new InetSocketAddress(this.getPort()));//绑定到指定端口
                        serverSocketChannel.configureBlocking(false);//配置为非阻塞模式
                        Selector selector = Selector.open();//打开选择器
                        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//注册，让选择器监控可接受连接事件
                        logger.info("打开被监控端口：{}",this.getPort());
                        while(true){
                            if(!runFlag){
                                break;
                            }
                            selector.select(2000l);
                            Set<SelectionKey> keySet = selector.selectedKeys();//获取发生事件的键集
                            Iterator<SelectionKey> iterator = keySet.iterator();
                            while(iterator.hasNext()) {//遍历键集
                                SelectionKey key = iterator.next();
                                if (key.isAcceptable()) {//发生可接受连接事件，
                                    SocketChannel sc = serverSocketChannel.accept();//接受socket连接 ，ServerSocketChannel只支持SelectionKey.OP_ACCEPT
                                    sc.close();
                                }
                                iterator.remove();
                            }
                        }
                    } catch (Exception e) {

                    } finally {
                        this.logger.info("被监控端口已销毁：" + this.port);
                        if (serverSocketChannel != null) {
                            serverSocketChannel.close();
                        }
                    }

                }
                sleep(1000L);
            }

        }
        catch (Exception e)
        {
            this.logger.error("被监控端口被占用，退出：", e);
            System.exit(0);
        }
    }
}
