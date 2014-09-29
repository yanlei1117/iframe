package com.asiainfo.dbx.ln.component.exception.ftp;

/**
 * Created by yanlei on 2014/8/18.
 */
public class FtpLoginError  extends RuntimeException {
    public FtpLoginError(String serverIp,String serverPort,String userName,String password,boolean loginFlag){
        super("login ftp server:"+serverIp+":"+serverPort+" use "+userName+"/"+password+",result="+loginFlag);
    }
    public FtpLoginError(String serverIp,String serverPort,String userName,String password,boolean loginFlag,boolean positive){
        super("login ftp server:"+serverIp+":"+serverPort+" use "+userName+"/"+password+",result="+loginFlag+",but positive mode is "+positive);
    }
}
