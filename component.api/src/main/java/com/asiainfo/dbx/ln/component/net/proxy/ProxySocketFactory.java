package com.asiainfo.dbx.ln.component.net.proxy;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by yanlei on 2014/8/20.
 */
public class ProxySocketFactory extends SocketFactory {
    private final Logger logger = LoggerFactory.getLogger(ProxySocketFactory.class);

    SocksProxy socksProxy = null;

    public SocksProxy getSocksProxy() {
        return socksProxy;
    }

    public void setSocksProxy(SocksProxy socksProxy) {
        this.socksProxy = socksProxy;
    }

    @Override
    public Socket createSocket(String s, int i) throws IOException, UnknownHostException {
        try {
            return new Socket(socksProxy.createProxy());
        } catch (Exception e) {
            logger.error("", e);
            if (e instanceof IOException) {
                throw (IOException) e;
            } else if (e instanceof UnknownHostException) {
                throw (UnknownHostException) e;
            } else {
                throw new IOException(e.getMessage());
            }

        }

    }

    @Override
    public Socket createSocket(String s, int i, InetAddress inetAddress, int i2) throws IOException, UnknownHostException {
        return SocketFactory.getDefault().createSocket(s,i,inetAddress,i2);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return SocketFactory.getDefault().createSocket(inetAddress,i);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        return SocketFactory.getDefault().createSocket(inetAddress,i,inetAddress2,i2);
    }
}