package com.asiainfo.dbx.ln.component.file;

import java.io.File;
import java.util.List;

/**
 * Created by yanlei on 2014/9/23.
 */
public interface FileService {
    public List<File> listFile(String localPath,String localFileNameRegex) throws Exception;
    public boolean deleteFile(String localPath,String localFileNameRegex)throws Exception;
    public boolean deleteDir(String localPath) throws Exception ;
    public boolean renameFile(String localPath,String localFileNameRegex,String destFileNameRegex)throws Exception;
    public boolean renameDir(String localPath,  String destDirName) throws Exception;
    public boolean moveFile(String localPath,String localFileNameRegex,String destLocalPath)throws Exception;
    public boolean moveDir(String localPath,  String destLocalPath) throws Exception;
    public boolean copyFile(String localPath,String localFileNameRegex,String destLocalPath)throws Exception;
    public boolean copyDir(String localPath,  String destLocalPath) throws Exception;
    public boolean zipDir(String localPath, String localFileNameRegex, String zipFilePath, String zipFileName) throws Exception;
    public boolean zipFile(String localPath,String localFileNameRegex,String zipFilePath,String zipFileName)throws Exception;
    public boolean unZipFile(String localPath,String zipFileName,String destLocalPath)throws Exception;
}
