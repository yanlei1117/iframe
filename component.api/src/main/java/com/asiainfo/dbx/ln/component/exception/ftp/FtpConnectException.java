package com.asiainfo.dbx.ln.component.exception.ftp;

/**
 * Created by yanlei on 2014/8/18.
 */
public class FtpConnectException extends RuntimeException{
    public FtpConnectException(String serverIp,String serverPort,int replyCode){
        super("connect to server: "+serverIp+":"+serverPort+",replyCode="+replyCode);
    }
}
