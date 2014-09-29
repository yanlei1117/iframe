package com.asiainfo.dbx.ln.component.net.ftp;

/**
 * Created by yanlei on 2014/8/18.
 */
public class FtpConfig {

    public static final String SYST_UNIX  = "UNIX";


    public static final String SYST_VMS   = "VMS";

    public static final String SYST_NT    = "WINDOWS";


    public static final String SYST_OS2   = "OS/2";


    public static final String SYST_OS400 = "OS/400";


    public static final String SYST_AS400 = "AS/400";

    public static final String SYST_MVS = "MVS";

    public static final String SYST_L8 = "TYPE: L8";

    public static final String SYST_NETWARE = "NETWARE";


    String serverIp;
    String username;
    String password;
    int serverPort=21;
    String serverSystem;
    String serverLanguage;
    String serverEncoding;
    String serverPathSpliter;
    int bufferSize;
    int receiveBufferSize;
    int sendBufferSize;
    int sendDataSocketBufferSize;
    int receieveDataSocketBufferSize;
    int connectTimeout = 0;
    int dataTimeout=0;
    int defaultTimeout=0;
    int SoTimeout=0;

    public int getDataTimeout() {
        return dataTimeout;
    }

    public void setDataTimeout(int dataTimeout) {
        this.dataTimeout = dataTimeout;
    }

    public int getDefaultTimeout() {
        return defaultTimeout;
    }

    public void setDefaultTimeout(int defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    public int getSoTimeout() {
        return SoTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        SoTimeout = soTimeout;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    boolean passiveeMode= true;

    public boolean isPassiveeMode() {
        return passiveeMode;
    }

    public void setPassiveeMode(boolean passiveeMode) {
        this.passiveeMode = passiveeMode;
    }

    public int getReceiveBufferSize() {
        return receiveBufferSize;
    }

    public void setReceiveBufferSize(int receiveBufferSize) {
        this.receiveBufferSize = receiveBufferSize;
    }

    public int getSendBufferSize() {
        return sendBufferSize;
    }

    public void setSendBufferSize(int sendBufferSize) {
        this.sendBufferSize = sendBufferSize;
    }

    public int getSendDataSocketBufferSize() {
        return sendDataSocketBufferSize;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setSendDataSocketBufferSize(int sendDataSocketBufferSize) {
        this.sendDataSocketBufferSize = sendDataSocketBufferSize;
    }

    public int getReceieveDataSocketBufferSize() {
        return receieveDataSocketBufferSize;
    }

    public void setReceieveDataSocketBufferSize(int receieveDataSocketBufferSize) {
        this.receieveDataSocketBufferSize = receieveDataSocketBufferSize;
    }

    public String getServerPathSpliter() {
        if(this.serverPathSpliter == null){
            this.serverPathSpliter="/";
        }
        return serverPathSpliter;
    }

    public void setServerPathSpliter(String serverPathSpliter) {
        this.serverPathSpliter = serverPathSpliter;
    }

    public String getServerSystem() {
        return serverSystem;
    }

    public void setServerSystem(String serverSystem) {
        this.serverSystem = serverSystem;
    }

    public String getServerLanguage() {
        return serverLanguage;
    }

    public void setServerLanguage(String serverLanguage) {
        this.serverLanguage = serverLanguage;
    }

    public String getServerEncoding() {
        return serverEncoding;
    }

    public void setServerEncoding(String serverEncoding) {
        this.serverEncoding = serverEncoding;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

}
