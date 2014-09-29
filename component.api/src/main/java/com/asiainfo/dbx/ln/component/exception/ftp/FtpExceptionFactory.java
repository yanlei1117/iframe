package com.asiainfo.dbx.ln.component.exception.ftp;

/**
 * Created by yanlei on 2014/8/18.
 */
public class FtpExceptionFactory {
    public static FtpMakeDirectoryException createFtpMakeDirectoryException(String serverParentPath,String dirNameToCreate){
        return new FtpMakeDirectoryException(serverParentPath,dirNameToCreate);
    }
    public static FtpConnectException createFtpConnectException(String serverIp,String serverPort,int replyCode){
        return new FtpConnectException(serverIp,serverPort,replyCode);
    }
    public static  FtpDownLoadFileLengthError createFtpDownLoadFileLengthError(String serverFile, long serverfileLength, String localFile, long localFileLength){
     return    new FtpDownLoadFileLengthError(serverFile,serverfileLength,localFile,localFileLength);

    }
    public static FtpDownloadFileFailException createFtpDownloadFileError(String serverFile,String localFile){
        return new FtpDownloadFileFailException(serverFile,localFile);
    }
    public static FtpServerPathNotFoundException createFtpServerPathNotFoundException(String serverFilePath){
        return new FtpServerPathNotFoundException(serverFilePath);
    }
    public static FtpMoveFileException createFtpMoveFileException(String srcServerPath,String srcServerFileName, String destServerPath,  String destServerFileName){
        return new FtpMoveFileException(srcServerPath,srcServerFileName,destServerPath,destServerFileName);
    }
    public static FtpCreateServerPathException createFtpCreateServerPathException(String serverPath){
        return new FtpCreateServerPathException(serverPath);
    }
    public static FtpUploadFileFailException createFtpUploadFileFailException(String localFilePath,String remoteFilePath){
        return new FtpUploadFileFailException(localFilePath,remoteFilePath);
    }
    public static FtpDeleteFileFailException createFtpDeleteFileFailException(String remoteFile){
        return new FtpDeleteFileFailException( remoteFile);
    }
    public static FtpUploadFileLengthException createFtpUploadFileLengthException(String serverFile, long serverfileLength, String localFile, long localFileLength){
        return    new FtpUploadFileLengthException(serverFile,serverfileLength,localFile,localFileLength);
    }
}
