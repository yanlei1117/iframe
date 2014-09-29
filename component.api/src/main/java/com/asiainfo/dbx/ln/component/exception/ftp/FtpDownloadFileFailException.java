package com.asiainfo.dbx.ln.component.exception.ftp;

/**
 * Created by yanlei on 2014/8/18.
 */
public class FtpDownloadFileFailException extends RuntimeException{
    public FtpDownloadFileFailException(String serverFile, String localFile){
        super("download file from  "+serverFile+" to  "+localFile+" ,retrieve fail ");
    }
}
