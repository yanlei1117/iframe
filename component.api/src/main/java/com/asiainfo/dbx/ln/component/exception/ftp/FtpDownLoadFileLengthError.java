package com.asiainfo.dbx.ln.component.exception.ftp;

/**
 * Created by Administrator on 2014/8/18.
 */
public class FtpDownLoadFileLengthError extends RuntimeException{
    public FtpDownLoadFileLengthError(String serverFile, long serverfileLength, String localFile, long localFileLength){
        super("the length("+localFileLength+") of the local file named "+localFile+"is not equal to length ("+serverfileLength+") of the server file named "+serverFile);
    }
}
