package com.asiainfo.dbx.ln.component.net.ftp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by yanlei on 2014/8/28.
 */
public class TestVindonnServer {
    Logger logger = LoggerFactory.getLogger(TestVindonnServer.class);
    private int getPort(){
        return 80;
    }
    public void start(){
        try {
            File file = new File("windonn.req");
            File file1 = new File("windonn.res");
            ByteBuffer readBuffer = ByteBuffer.allocate(1024*1024);
            FileOutputStream fos = new FileOutputStream(file);
            FileOutputStream fos1 = new FileOutputStream(file1);
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();//打开通道
            serverSocketChannel.socket().bind(new InetSocketAddress(this.getPort()));//绑定到指定端口
            serverSocketChannel.configureBlocking(false);//配置为非阻塞模式
            Selector selector = Selector.open();//打开选择器
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//注册，让选择器监控可接受连接事件
            logger.info("打开被监控端口：{}", this.getPort());
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
                        fos.write(data);
                        fos.flush();
                        logger.info(new String (data));
                        data =  write((data));
                        fos1.write(data);
                        fos1.flush();
                        //if(new String (data).indexOf("java.yanlei@163.com")!= -1){
                         sc.write(ByteBuffer.wrap( data));
                        //}


                    }
                    iterator.remove();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    SocketChannel vindonnsocketChannel = null;
    private SocketChannel getVindonnsocketChannel() throws Exception {
        if (vindonnsocketChannel == null) {
            vindonnsocketChannel = SocketChannel.open();
            vindonnsocketChannel.connect(new InetSocketAddress("114.215.128.190", 80));

        }
        return vindonnsocketChannel;
    }

    public byte [] write(byte [] data) throws Exception {
        this.getVindonnsocketChannel().write(ByteBuffer.wrap(data));
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        this.getVindonnsocketChannel().read(byteBuffer);
        byteBuffer.flip();
        data= new byte [byteBuffer.remaining()];
        byteBuffer.get(data);
        logger.info("read from server length={}:{}",data.length,new String (data));
        return data;
    }
    /*
     * fos
     */
  /*  public void toServer(){
        try {
            SocketChannel socketChannel = SocketChannel.open();
            boolean connect = socketChannel.connect(new InetSocketAddress("114.215.128.190", 80));
            if (connect) {
                ByteBuffer readBuffer = ByteBuffer.allocate(5048);
                byte []data1 = getLoginString().getBytes();

                logger.info("data1.length={}",data1.length );
                int writeInt1 = socketChannel.write(ByteBuffer.wrap(data1));

                logger.info("writeInt1.length={}",writeInt1 );
                int readNum = socketChannel.read(readBuffer);
                logger.info("readnum from server:" + readNum);
                readBuffer.flip();
                byte []  data = new byte[readBuffer.remaining()];
                readBuffer.get(data);
                logger.info("read from server:{}", new String(data));
                readBuffer.flip();
            }
            socketChannel.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }*/
    private byte [] replaceValue(byte [] data){
      String str = new String(data);
      if(str.indexOf("&lt;?xml version=\"1.0\" encoding=\"utf-8\" ?&gt;&lt;root&gt;&lt;item&gt;&lt;step&gt")!= -1){
         byte [] newData  =  convertValue(str).getBytes();
          System.out.println("add:"+(totalDest-totalSource));
          return newData;
      }else {
          return data;
      }
    }
    int totalSource = 0;
    int totalDest = 0;
    private String convertValue(String str){
        StringBuffer sb = new StringBuffer();
        Scanner scanner = new Scanner(str);
        scanner.useDelimiter("&gt;\\d+&lt;");
        while(scanner.hasNext()){
            String str1 = scanner.next();
            sb.append(str1);
            String matcher = scanner.findInLine("&gt;\\d+&lt;");
            if(matcher != null){
                int i = Integer.parseInt(matcher.substring(4, matcher.length() - 4));
                if(str1.endsWith("step")) {
                    totalSource += i;
                    totalDest += (i * 2);
                }
                sb.append("&gt;"+i*2+"&lt;");
            }

        }
        return sb.toString();
    }

    public String getRequestString(){
        String responseString ="<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><SyncData xmlns=\"http://tempuri.org/\"><LoginID>java.yanlei@163.com</LoginID><DeviceID>PCSYNC_95781D04A578</DeviceID><DataXML>&lt;?xml version=\"1.0\" encoding=\"utf-8\" ?&gt;&lt;root&gt;&lt;item&gt;&lt;step&gt;953&lt;/step&gt;&lt;distance&gt;553&lt;/distance&gt;&lt;calorie&gt;35&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-27 6:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;8584&lt;/step&gt;&lt;distance&gt;8072&lt;/distance&gt;&lt;calorie&gt;524&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-27 7:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;94&lt;/step&gt;&lt;distance&gt;76&lt;/distance&gt;&lt;calorie&gt;4&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-27 8:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;5&lt;/step&gt;&lt;distance&gt;5&lt;/distance&gt;&lt;calorie&gt;0&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-27 11:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;5&lt;/step&gt;&lt;distance&gt;5&lt;/distance&gt;&lt;calorie&gt;0&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-27 15:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;5&lt;/step&gt;&lt;distance&gt;5&lt;/distance&gt;&lt;calorie&gt;0&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-27 16:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;3888&lt;/step&gt;&lt;distance&gt;2806&lt;/distance&gt;&lt;calorie&gt;182&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-27 17:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;8127&lt;/step&gt;&lt;distance&gt;7405&lt;/distance&gt;&lt;calorie&gt;481&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-27 18:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;172&lt;/step&gt;&lt;distance&gt;145&lt;/distance&gt;&lt;calorie&gt;9&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-27 19:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;13&lt;/step&gt;&lt;distance&gt;13&lt;/distance&gt;&lt;calorie&gt;0&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-27 21:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;5&lt;/step&gt;&lt;distance&gt;4&lt;/distance&gt;&lt;calorie&gt;0&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-27 22:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;1474&lt;/step&gt;&lt;distance&gt;1009&lt;/distance&gt;&lt;calorie&gt;65&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-28 6:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;4932&lt;/step&gt;&lt;distance&gt;4650&lt;/distance&gt;&lt;calorie&gt;302&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-28 7:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;5430&lt;/step&gt;&lt;distance&gt;4690&lt;/distance&gt;&lt;calorie&gt;304&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-28 8:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;8&lt;/step&gt;&lt;distance&gt;8&lt;/distance&gt;&lt;calorie&gt;0&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-28 9:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;57&lt;/step&gt;&lt;distance&gt;41&lt;/distance&gt;&lt;calorie&gt;2&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-28 10:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;25&lt;/step&gt;&lt;distance&gt;24&lt;/distance&gt;&lt;calorie&gt;1&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-28 15:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;5663&lt;/step&gt;&lt;distance&gt;4798&lt;/distance&gt;&lt;calorie&gt;311&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-28 17:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;9003&lt;/step&gt;&lt;distance&gt;8166&lt;/distance&gt;&lt;calorie&gt;530&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-28 18:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;773&lt;/step&gt;&lt;distance&gt;635&lt;/distance&gt;&lt;calorie&gt;41&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-28 19:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;618&lt;/step&gt;&lt;distance&gt;508&lt;/distance&gt;&lt;calorie&gt;33&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-28 20:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;576&lt;/step&gt;&lt;distance&gt;495&lt;/distance&gt;&lt;calorie&gt;32&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-29 5:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;7280&lt;/step&gt;&lt;distance&gt;6350&lt;/distance&gt;&lt;calorie&gt;412&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-29 6:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;7450&lt;/step&gt;&lt;distance&gt;6410&lt;/distance&gt;&lt;calorie&gt;416&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-29 7:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;6876&lt;/step&gt;&lt;distance&gt;6114&lt;/distance&gt;&lt;calorie&gt;397&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-29 8:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;25&lt;/step&gt;&lt;distance&gt;32&lt;/distance&gt;&lt;calorie&gt;2&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-29 9:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;32&lt;/step&gt;&lt;distance&gt;23&lt;/distance&gt;&lt;calorie&gt;1&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 0:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;206&lt;/step&gt;&lt;distance&gt;186&lt;/distance&gt;&lt;calorie&gt;12&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 5:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;1281&lt;/step&gt;&lt;distance&gt;755&lt;/distance&gt;&lt;calorie&gt;49&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 6:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;144&lt;/step&gt;&lt;distance&gt;129&lt;/distance&gt;&lt;calorie&gt;8&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 7:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;267&lt;/step&gt;&lt;distance&gt;197&lt;/distance&gt;&lt;calorie&gt;12&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 8:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;24&lt;/step&gt;&lt;distance&gt;25&lt;/distance&gt;&lt;calorie&gt;1&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 9:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;739&lt;/step&gt;&lt;distance&gt;519&lt;/distance&gt;&lt;calorie&gt;33&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 10:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;2232&lt;/step&gt;&lt;distance&gt;1382&lt;/distance&gt;&lt;calorie&gt;89&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 11:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;2714&lt;/step&gt;&lt;distance&gt;1541&lt;/distance&gt;&lt;calorie&gt;100&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 12:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;3907&lt;/step&gt;&lt;distance&gt;2143&lt;/distance&gt;&lt;calorie&gt;139&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 13:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;2137&lt;/step&gt;&lt;distance&gt;1230&lt;/distance&gt;&lt;calorie&gt;79&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 14:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;164&lt;/step&gt;&lt;distance&gt;131&lt;/distance&gt;&lt;calorie&gt;8&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 15:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;1175&lt;/step&gt;&lt;distance&gt;788&lt;/distance&gt;&lt;calorie&gt;51&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 16:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;870&lt;/step&gt;&lt;distance&gt;562&lt;/distance&gt;&lt;calorie&gt;36&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 17:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;529&lt;/step&gt;&lt;distance&gt;389&lt;/distance&gt;&lt;calorie&gt;25&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 18:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;557&lt;/step&gt;&lt;distance&gt;450&lt;/distance&gt;&lt;calorie&gt;29&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 19:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;547&lt;/step&gt;&lt;distance&gt;382&lt;/distance&gt;&lt;calorie&gt;24&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 20:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;274&lt;/step&gt;&lt;distance&gt;213&lt;/distance&gt;&lt;calorie&gt;13&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 21:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;78&lt;/step&gt;&lt;distance&gt;61&lt;/distance&gt;&lt;calorie&gt;3&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 22:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;269&lt;/step&gt;&lt;distance&gt;196&lt;/distance&gt;&lt;calorie&gt;12&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-23 23:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;215&lt;/step&gt;&lt;distance&gt;162&lt;/distance&gt;&lt;calorie&gt;10&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-24 0:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;410&lt;/step&gt;&lt;distance&gt;322&lt;/distance&gt;&lt;calorie&gt;20&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-24 6:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;656&lt;/step&gt;&lt;distance&gt;443&lt;/distance&gt;&lt;calorie&gt;28&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-24 7:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;886&lt;/step&gt;&lt;distance&gt;613&lt;/distance&gt;&lt;calorie&gt;39&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-24 8:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;1318&lt;/step&gt;&lt;distance&gt;821&lt;/distance&gt;&lt;calorie&gt;53&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-24 9:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;183&lt;/step&gt;&lt;distance&gt;140&lt;/distance&gt;&lt;calorie&gt;9&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-24 10:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;36&lt;/step&gt;&lt;distance&gt;32&lt;/distance&gt;&lt;calorie&gt;2&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-24 11:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;24&lt;/step&gt;&lt;distance&gt;24&lt;/distance&gt;&lt;calorie&gt;1&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-24 15:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;1419&lt;/step&gt;&lt;distance&gt;1082&lt;/distance&gt;&lt;calorie&gt;70&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-24 16:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;672&lt;/step&gt;&lt;distance&gt;519&lt;/distance&gt;&lt;calorie&gt;33&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-24 17:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;40&lt;/step&gt;&lt;distance&gt;41&lt;/distance&gt;&lt;calorie&gt;2&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-24 18:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;74&lt;/step&gt;&lt;distance&gt;52&lt;/distance&gt;&lt;calorie&gt;3&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-24 19:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;616&lt;/step&gt;&lt;distance&gt;428&lt;/distance&gt;&lt;calorie&gt;27&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-24 20:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;51&lt;/step&gt;&lt;distance&gt;49&lt;/distance&gt;&lt;calorie&gt;3&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-24 21:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;714&lt;/step&gt;&lt;distance&gt;458&lt;/distance&gt;&lt;calorie&gt;29&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-25 6:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;390&lt;/step&gt;&lt;distance&gt;264&lt;/distance&gt;&lt;calorie&gt;17&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-25 7:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;2366&lt;/step&gt;&lt;distance&gt;1359&lt;/distance&gt;&lt;calorie&gt;88&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-25 8:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;135&lt;/step&gt;&lt;distance&gt;91&lt;/distance&gt;&lt;calorie&gt;5&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-25 9:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;223&lt;/step&gt;&lt;distance&gt;147&lt;/distance&gt;&lt;calorie&gt;9&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-25 10:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;539&lt;/step&gt;&lt;distance&gt;351&lt;/distance&gt;&lt;calorie&gt;22&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-25 11:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;15&lt;/step&gt;&lt;distance&gt;12&lt;/distance&gt;&lt;calorie&gt;0&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-25 12:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;24&lt;/step&gt;&lt;distance&gt;21&lt;/distance&gt;&lt;calorie&gt;1&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-25 13:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;4969&lt;/step&gt;&lt;distance&gt;3825&lt;/distance&gt;&lt;calorie&gt;248&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-25 17:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;3170&lt;/step&gt;&lt;distance&gt;2715&lt;/distance&gt;&lt;calorie&gt;176&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-25 18:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;728&lt;/step&gt;&lt;distance&gt;597&lt;/distance&gt;&lt;calorie&gt;38&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-25 19:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;183&lt;/step&gt;&lt;distance&gt;152&lt;/distance&gt;&lt;calorie&gt;9&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-25 20:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;19&lt;/step&gt;&lt;distance&gt;14&lt;/distance&gt;&lt;calorie&gt;0&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-26 5:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;1030&lt;/step&gt;&lt;distance&gt;699&lt;/distance&gt;&lt;calorie&gt;45&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-26 6:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;7703&lt;/step&gt;&lt;distance&gt;7215&lt;/distance&gt;&lt;calorie&gt;468&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-26 7:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;1650&lt;/step&gt;&lt;distance&gt;978&lt;/distance&gt;&lt;calorie&gt;63&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-26 8:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;30&lt;/step&gt;&lt;distance&gt;20&lt;/distance&gt;&lt;calorie&gt;1&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-26 9:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;410&lt;/step&gt;&lt;distance&gt;266&lt;/distance&gt;&lt;calorie&gt;17&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-26 10:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;404&lt;/step&gt;&lt;distance&gt;279&lt;/distance&gt;&lt;calorie&gt;18&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-26 11:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;123&lt;/step&gt;&lt;distance&gt;94&lt;/distance&gt;&lt;calorie&gt;6&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-26 13:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;203&lt;/step&gt;&lt;distance&gt;149&lt;/distance&gt;&lt;calorie&gt;9&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-26 14:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;154&lt;/step&gt;&lt;distance&gt;103&lt;/distance&gt;&lt;calorie&gt;6&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-26 15:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;105&lt;/step&gt;&lt;distance&gt;75&lt;/distance&gt;&lt;calorie&gt;4&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-26 16:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;6668&lt;/step&gt;&lt;distance&gt;5613&lt;/distance&gt;&lt;calorie&gt;364&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-26 17:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;3355&lt;/step&gt;&lt;distance&gt;2849&lt;/distance&gt;&lt;calorie&gt;185&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-26 18:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;item&gt;&lt;step&gt;122&lt;/step&gt;&lt;distance&gt;113&lt;/distance&gt;&lt;calorie&gt;7&lt;/calorie&gt;&lt;storey&gt;0&lt;/storey&gt;&lt;sleep&gt;0&lt;/sleep&gt;&lt;acttime&gt;2014-8-26 19:00:00&lt;/acttime&gt;&lt;/item&gt;&lt;/root&gt;</DataXML><verCode>VD2013</verCode></SyncData></soap:Body></soap:Envelope>";
        return responseString;
    }
    public void testReplace(){

        byte [] data = replaceValue(getRequestString().getBytes());
        String newValue = new String (data);
        System.out.println(getRequestString());
        System.out.println(newValue);
        System.out.println(newValue.equals(getRequestString()));
    }
     public static void main(String []args ){
           new TestVindonnServer().start();
         // new TestSocket().toServer();
       //  new TestSocket().testReplace();
     }

}
