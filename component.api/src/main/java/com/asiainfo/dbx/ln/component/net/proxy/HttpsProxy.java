package com.asiainfo.dbx.ln.component.net.proxy;

import java.net.Proxy;

/**
 * Created by yanlei on 2014/8/15.
 */
public class HttpsProxy extends  NetProxy{
    @Override
    public String getProxyHostPropertyName() {
        return "https.proxyHost";
    }

    @Override
    public String getProxyPortPropertyName() {
        return "https.proxyPort";
    }

    @Override
    public String getNonProxyHostsPropertyName() {
        return "https.nonProxyHosts";
    }
    @Override
    public String getProxySetFlag() {
        return "https.proxySet";
    }
    @Override
    public Proxy.Type getProxyType() {
        return Proxy.Type.HTTP;
    }
}
