package com.asiainfo.dbx.ln.component.net.proxy;

import java.net.Proxy;

/**
 * Created by yanlei on 2014/8/15.
 */
public class FtpProxy extends NetProxy{
    @Override
    public String getProxyHostPropertyName() {
        return "ftp.proxyHost";
    }

    @Override
    public String getProxyPortPropertyName() {
        return "ftp.proxyPort";
    }

    @Override
    public String getNonProxyHostsPropertyName() {
        return "ftp.nonProxyHosts";
    }

    @Override
    public String getProxySetFlag() {
        return "ftp.proxySet";
    }

    @Override
    public Proxy.Type getProxyType() {
        return Proxy.Type.HTTP;
    }
    @Override
    public void applyProxy() throws Exception {
        System.setProperty("ftpProxyPort", this.getProxyPort());

        System.setProperty("ftpProxyHost", this.getProxyHost());

        System.setProperty("ftpProxySet", "true");
        System.setProperty("proxySet", "true");

        super.applyProxy();


    }
}
