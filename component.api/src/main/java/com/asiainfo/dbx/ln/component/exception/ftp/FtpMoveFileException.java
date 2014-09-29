package com.asiainfo.dbx.ln.component.exception.ftp;

/**
 * Created by yanlei on 2014/8/21.
 */
public class FtpMoveFileException extends RuntimeException{
    public FtpMoveFileException(String srcServerPath,String srcServerFileName, String destServerPath,  String destServerFileName){
        super(" move sever file from   "+srcServerPath+"/"+srcServerFileName+" to "+destServerPath+"/"+destServerFileName+"' fail");
    }
}
