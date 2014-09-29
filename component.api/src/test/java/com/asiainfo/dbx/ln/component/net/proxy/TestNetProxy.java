package com.asiainfo.dbx.ln.component.net.proxy;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.net.SocketFactory;
import java.net.*;
import java.util.List;

import static org.junit.Assert.assertThat;
import  static org.hamcrest.Matchers.*;
/**
 * Created by Administrator on 2014/8/20.
 */
@RunWith(JUnit4.class)
public class TestNetProxy {
    private final Logger logger = LoggerFactory.getLogger(TestNetProxy.class);
    @Test
    public void testHttpProxy(){
        try {
            FtpProxy ftpProxy = new FtpProxy();
            ftpProxy.setProxyHost("201.38.21.32");
            ftpProxy.setProxyPort("2321");
            ftpProxy.applyProxy();
            List<Proxy> proxyList =  ftpProxy.getProxyForUrl("ftp://193.com");

            Proxy proxy = proxyList.get(0);
            assertThat(proxy.address().toString(),equalTo("201.38.21.32:2321"));

            assertThat(proxyList.size(),greaterThan(0));
            /*
            URL url = new URL("ftp://193.com");
            url.openConnection().getInputStream().read();*/




/*
            Socket socket =new Socket(ftpProxy.createProxy());
            socket.connect(new InetSocketAddress(InetAddress.getByName("193.com"),100));
            socket.close();
*/
        }catch(Throwable e){
            logger.error("",e);
            Assert.fail();
        }

    }

    @Test
    public void testSocketProxy(){
        try {
            SocksProxy socksProxy = new SocksProxy();
            socksProxy.setProxyHost("201.38.21.32");
            socksProxy.setProxyPort("2321");
            Socket socket =new Socket(socksProxy.createProxy());
            socket.connect(new InetSocketAddress(InetAddress.getByName("www.baidu.com"),80));
            socket.close();
        }catch(Throwable e){
            logger.error("",e);
            Assert.fail();
        }
    }

}
