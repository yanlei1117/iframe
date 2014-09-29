package com.asiainfo.dbx.ln.component.net.proxy;

import java.net.Proxy;

/**
 * Created by yanlei on 2014/8/15.
 */
public class SocksProxy extends NetProxy {
    @Override
    public String getProxyHostPropertyName() {
        return "socks.proxyHost";
    }

    @Override
    public String getProxyPortPropertyName() {
        return "socks.proxyPort";
    }

    @Override
    public String getNonProxyHostsPropertyName() {
        return "socks.nonProxyHosts";
    }
    @Override
    public String getProxySetFlag() {
        return "socks.proxySet";
    }
    @Override
    public Proxy.Type getProxyType() {
        return Proxy.Type.SOCKS;
    }
}
