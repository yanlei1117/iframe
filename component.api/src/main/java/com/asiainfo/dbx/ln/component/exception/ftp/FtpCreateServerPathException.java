package com.asiainfo.dbx.ln.component.exception.ftp;

/**
 * Created by yanlei on 2014/8/21.
 */
public class FtpCreateServerPathException extends RuntimeException{
    public FtpCreateServerPathException(String serverPath){
        super("create server file path ‘“+serverPath+”’ fail");
    }
}
