package com.asiainfo.dbx.ln.component.net.ftp;

import com.asiainfo.dbx.ln.component.net.ftp.FtpOperatorService;
import com.asiainfo.dbx.ln.component.util.AppFtpUtils;

import java.io.File;
import java.util.List;

/**
 * Created by yanlei on 2014/9/22.
 */
public class FtpOperatorServiceImpl implements FtpOperatorService {
    @Override
    public List<FtpFile> getFileListInDir(String serverId, String serverPath) throws Throwable {
        return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class,serverId).getFileListInDir(serverPath);
    }

    @Override
    public List<FtpFile> getFileListInDir(String serverId, String serverPath, String fileNameRegex) throws Throwable {
        return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class,serverId).getFileListInDir(serverPath,fileNameRegex);
    }

    @Override
    public File downloadFile(String serverId, String serverPath, String serverFileName, String localPath, String localFileName) throws Throwable {
        return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class,serverId).downloadFile(serverPath, serverFileName, localPath, localFileName);
    }

    @Override
    public List<File> downloadAllFile(String serverId, String serverPath, String localPath) throws Throwable {
        return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class,serverId).downloadAllFile(serverPath, localPath);
    }

    @Override
    public List<File> downloadAllFile(String serverId, String serverPath, String serverFileNameRegex, String localPath) throws Throwable {
        return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class,serverId).downloadAllFile(serverPath,serverFileNameRegex,localPath);
    }

    @Override
    public List<File> downloadAllFile(String serverId, String serverPath, String serverFileNameRegex, String localPath, boolean includeSubDir) throws Throwable {
        return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class,serverId).downloadAllFile(serverPath,serverFileNameRegex,localPath,includeSubDir);
    }

    @Override
    public int uploadFile(String serverId, String localPath, String localFileName, String serverPath, String serverFileName) throws Throwable {
        return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class,serverId).uploadFile(localPath, localFileName, serverPath, serverFileName);
    }

    @Override
    public int uploadAllFile(String serverId, String localPath, String fileNameRegex, String serverPath, boolean includeSubDir) throws Throwable {
        return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class,serverId).uploadAllFile(localPath, fileNameRegex, serverPath, includeSubDir);
    }

    @Override
    public int renameServerFile(String serverId, String serverPath, String srcServerFileName, String destServerFileName) throws Throwable {
        return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class,serverId).renameServerFile(serverPath, srcServerFileName, destServerFileName);
    }

    @Override
    public int renameAllServerFile(String serverId, String serverPath, String srcFileNameRegex, String destFileNameRegex, boolean includeSubDir) throws Throwable {
        return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class,serverId).renameAllServerFile(serverPath, srcFileNameRegex, destFileNameRegex, includeSubDir);
    }

    @Override
    public int moveServerFile(String serverId, String srcServerPath, String srcServerFileName, String destServerPath, String destServerFileName) throws Throwable {
        return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class,serverId).moveServerFile(srcServerPath, srcServerFileName, destServerPath, destServerFileName);
    }

    @Override
    public int moveAllServerFile(String serverId, String serverPath, String fileNameRegex, String destServerPath, boolean includeSubDir) throws Throwable {
        return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class,serverId).moveAllServerFile(serverPath, fileNameRegex, destServerPath, includeSubDir);
    }

    @Override
    public int moveDir(String serverId, String serverPath, String destServerPath) throws Throwable {
        return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class,serverId).moveDir(serverPath, destServerPath);
    }

    @Override
    public int deleteFile(String serverId, String serverPath, String serverFileName) throws Throwable {
        return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class,serverId).deleteFile(serverPath, serverFileName);
    }

    @Override
    public int deleteAllFile(String serverId, String serverPath, String fileNameRegex, boolean includeSubDir) throws Throwable {
        return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class,serverId).deleteAllFile(serverPath, fileNameRegex, includeSubDir);
    }

    @Override
    public int deleteDir(String serverId, String serverPath) throws Throwable {
        return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class,serverId).deleteDir(serverPath);
    }

    @Override
    public boolean close(String serverId) {
        try {
            return AppFtpUtils.getFtpOperator(FtpOperatorServiceImpl.class, serverId).close();
        }catch(Exception e){
            return false;
        }
    }
}
