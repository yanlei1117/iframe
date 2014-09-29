package com.asiainfo.dbx.ln.component.net.proxy;



import com.asiainfo.dbx.ln.component.exception.ExceptionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.asiainfo.dbx.ln.component.util.AppValidationUtils.*;

import java.net.*;
import java.util.List;
import java.util.Properties;

/**
 * 网络访问代理设置
 *
 * Created by yanlei on 2014/8/15.
 */
public abstract  class NetProxy {
    private Logger logger = LoggerFactory.getLogger(NetProxy.class);
    /**
     * 代理服务器的地址
     */
    private String proxyHost;
    /**
     * 代理服务器的端口
     */
    private String proxyPort ;
    /**
     * 不需要通过代理服务器访问的主机，可以使用*通配符，多个地址用|分隔
     */
    private String nonProxyHosts;
    /**
     * 登录代理服务器的用户名
     */
    private String userName;
    /**
     * 登录代理服务器的密码
     */
    private String password;

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getNonProxyHosts() {
        return nonProxyHosts;
    }

    public void setNonProxyHosts(String nonProxyHosts) {
        this.nonProxyHosts = nonProxyHosts;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected abstract String getProxyHostPropertyName();
    protected abstract String getProxyPortPropertyName();
    protected abstract String getNonProxyHostsPropertyName();
    protected abstract  String getProxySetFlag();
    public abstract  Proxy.Type getProxyType();

    /**
     * 创建代理
     * @return
     * @throws Exception
     */
    public Proxy createProxy() throws Exception{
        validProxyHostAndPort();
        Proxy proxy =  new Proxy(this.getProxyType(),new InetSocketAddress(InetAddress.getByName(this.getProxyHost()),Integer.parseInt(this.getProxyPort())));
        setAuthenticator();
        return proxy;
    }
    private void validProxyHostAndPort(){
        if(isEmpty(this.getProxyHost())){
            throw ExceptionFactory.createPropertyNotConfigedException("proxyHost");
        }
        if(isEmpty(this.getProxyPort())){
            throw ExceptionFactory.createPropertyNotConfigedException("proxyPort");
        }
    }
    /**
     * 设置（应用）HTTP代理
     */
    public void applyProxy() throws Exception{

        Properties systemProperties = System.getProperties();
        validProxyHostAndPort();
        systemProperties.setProperty(this.getProxyHostPropertyName(),this.getProxyHost());
        logger.info("applyProxy:{}={}",this.getProxyHostPropertyName(),systemProperties.getProperty(this.getProxyHostPropertyName()));

        systemProperties.setProperty(this.getProxyPortPropertyName(),this.getProxyPort());
        logger.info("applyProxy:{}={}",this.getProxyPortPropertyName(),systemProperties.getProperty(this.getProxyPortPropertyName()));

        systemProperties.setProperty(this.getProxySetFlag(),"true");

        logger.info("applyProxy:{}={}",this.getProxySetFlag(),systemProperties.getProperty(this.getProxySetFlag()));

        if(notEmpty(this.nonProxyHosts)){
            systemProperties.setProperty(this.getNonProxyHostsPropertyName(),this.getNonProxyHosts());
            logger.info("applyProxy:{}={}",this.getNonProxyHostsPropertyName(),systemProperties.getProperty(this.getNonProxyHostsPropertyName()));
        }else{
            logger.info("applyProxy:property named  '{}' not be configured",this.getNonProxyHostsPropertyName());
        }

        setAuthenticator();

    }

    public List<Proxy> getProxyForUrl(String url) throws Exception{
        URI uri = new URL(url).toURI();
        return ProxySelector.getDefault().select(uri);
    }
    private void setAuthenticator(){
        if(notEmpty(this.getUserName()) ){
            Authenticator.setDefault(new Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    String password = getPassword();
                    if(isNull(password)){
                        password ="";
                    }
                    String userName = getUserName().trim();
                    return new PasswordAuthentication(userName, password.toCharArray());
                }
            });
            logger.info("applyProxy:userName={}",this.getUserName());
            logger.info("applyProxy:password={}",this.getPassword());
        }else{
            logger.info("applyProxy:property named  'username' and 'password' not be configured");
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NetProxy{");
        sb.append("proxyType='").append(this.getProxyType()).append('\'');
        sb.append("proxyHost='").append(proxyHost).append('\'');
        sb.append(", proxyPort='").append(proxyPort).append('\'');
        sb.append(", nonProxyHosts='").append(nonProxyHosts).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
