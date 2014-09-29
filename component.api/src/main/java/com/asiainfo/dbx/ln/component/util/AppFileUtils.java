package com.asiainfo.dbx.ln.component.util;

import com.asiainfo.dbx.ln.component.exception.File.FileExceptionFactory;
import com.asiainfo.dbx.ln.component.file.FileService;
import com.asiainfo.dbx.ln.component.net.ftp.FtpOperatorService;

import java.io.File;

/**
 * Created by yanlei on 2014/9/23.
 */
public class AppFileUtils {

    public static boolean createLocalPathIfNotExist(File localPath) throws Exception{

        if(localPath.exists() || createLocalPathIfNotExist(localPath.getParentFile())&& localPath.mkdir()){
            return true;
        }else{
            throw  FileExceptionFactory.createIllegalFilePathException(localPath.getAbsolutePath());
        }
    }

    private static FileService fileService = null;
    public static FileService getFileService(Class classz) throws Exception{
        if(AppFileUtils.fileService == null){
            synchronized (AppFileUtils.class){
                if(AppFileUtils.fileService ==null) {
                    FileService fileService1 = (FileService) AppSpringUtils.getApplicationContextHolder().getBean("fileService");
                    if (fileService1 == null) {
                        throw new Exception("no bean named 'fileService'");
                    }
                    AppFileUtils.fileService = fileService1;
                }
            }
        }
        return AppFileUtils.fileService;
    }
}
