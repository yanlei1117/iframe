package com.asiainfo.dbx.ln.component.exception.ftp;

/**
 * Created by yanlei on 2014/8/18.
 */
public class FtpMakeDirectoryException extends RuntimeException {
    public FtpMakeDirectoryException(String serverPath,String createDirName){
         super("create directory named '"+createDirName+"' on path '"+serverPath+"' fail");
    }
}
