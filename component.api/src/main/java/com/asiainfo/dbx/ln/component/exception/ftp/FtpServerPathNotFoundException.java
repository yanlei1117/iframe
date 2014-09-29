package com.asiainfo.dbx.ln.component.exception.ftp;

/**
 * Created by yanlei on 2014/8/18.
 */
public class FtpServerPathNotFoundException extends RuntimeException{
    public FtpServerPathNotFoundException(String serverPath){
        super(" server file path named  '"+serverPath+"' not found");
    }
}
