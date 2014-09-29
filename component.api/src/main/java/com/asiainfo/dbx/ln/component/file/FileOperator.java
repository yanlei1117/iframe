package com.asiainfo.dbx.ln.component.file;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * 文件操作
 * Created by yanlei on 2014/9/12.
 */
public interface FileOperator {

    /**
     * 获取父文件操作对象
     * @return
     */
    public FileOperator  getParentFile ();

    /**
     * 获取父文件操作对象
     * @return
     */
    public List<FileOperator> getFileList ();

    /**
     * 获取当前操作的文件
     * @return
     */
    public File getFile();

    /**
     * 获取文件绝对路径
     * @return
     */
    public String getAbsolutePath();

    /**
     * 获取文件长度
     * @return
     */
    public long getLength();
    /**
     * 获取文件名称
     * @return
     */
    public String getFileName();

    /**
     * 删除文件
     * @return
     */
    public boolean delete();


    /**
     * 文件改名
     * @param newFileName
     * @return
     */
    public boolean rename(String newFileName);

    /**
     * 创建新文件
     * @throws IOException
     */
    public void createNewFile() throws IOException ;

    /**
     * 文件是否存在
     * @return
     * @throws IOException
     */
    public boolean isFileExist() throws IOException;


    /**
     * 文件移到指定目录
     * @param destDir
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean moveTo(String destDir)  throws Exception;
    public boolean copyFile(String destFile)throws Exception;
    public boolean zipFile(String zipFileName) throws Exception;
    public boolean unZipFile(String dir) throws Exception;
}
