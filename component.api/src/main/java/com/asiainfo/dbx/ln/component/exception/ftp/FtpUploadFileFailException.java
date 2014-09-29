package com.asiainfo.dbx.ln.component.exception.ftp;

/**
 * Created by yanlei on 2014/8/21.
 */
public class FtpUploadFileFailException extends RuntimeException{
    public FtpUploadFileFailException(  String localFileName,String serverFileName){
        super(" ftp upload file  from local ("+localFileName+") to remote("+serverFileName+")  fail");
    }
}
