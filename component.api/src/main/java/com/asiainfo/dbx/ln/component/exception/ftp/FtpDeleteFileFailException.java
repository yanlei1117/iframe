package com.asiainfo.dbx.ln.component.exception.ftp;

/**
 * Created by yanlei on 2014/8/21.
 */
public class FtpDeleteFileFailException extends  RuntimeException{
    public FtpDeleteFileFailException(String remoteFile){
        super(" delete remote file '"+remoteFile+"' fail");
    }
}
