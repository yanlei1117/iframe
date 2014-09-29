package com.asiainfo.dbx.ln.component.exception.ftp;

/**
 * Created by yanlei on 2014/8/21.
 */
public class FtpUploadFileLengthException extends RuntimeException{
    public FtpUploadFileLengthException(String serverFile, long serverfileLength, String localFile, long localFileLength){
        super("the length("+localFileLength+") of the local file named "+localFile+"is not equal to length ("+serverfileLength+") of the server file named "+serverFile);
    }
}
