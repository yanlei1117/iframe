package com.asiainfo.dbx.ln.component.net.proxy;

import java.net.Proxy;

/**
 * Created by yanlei on 2014/8/15.
 */
public class HttpProxy extends NetProxy{
    @Override
    public String getProxyHostPropertyName() {
        return "http.proxyHost";
    }

    @Override
    public String getProxyPortPropertyName() {
        return "http.proxyPort";
    }

    @Override
    public String getNonProxyHostsPropertyName() {
        return "http.nonProxyHosts";
    }
    @Override
    public String getProxySetFlag() {
        return "http.proxySet";
    }

    @Override
    public Proxy.Type getProxyType() {
        return Proxy.Type.HTTP;
    }
}
