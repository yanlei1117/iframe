package com.asiainfo.dbx.ln.component.net.ftp;

import java.io.File;
import java.util.List;

/**
 *
 * FTP文件操作
 *
 *
 * Created by yanlei on 2014/8/15.
 */
public interface FtpOperator {

    /**
     * 获取FTP服务器上，指定目录下的所有匹配的文件列表
     * @param serverPath  FTP服务器上的指定目录
     * @return  目录下的文件列表 ,如果目录下没有文件则返回null
     * @throws Throwable
     *           FtpServerPathNotFoundException 服务器上的指定目录不存在
     */
    public List<FtpFile> getFileListInDir(String serverPath) throws Throwable;

    /**
     * 获取FTP服务器上，指定目录下的所有匹配的文件列表
     * @param serverPath  FTP服务器上的指定目录
     * @param fileNameRegex 文件名匹配正则表达式
     * @return 目录下的文件列表 ,如果目录下没有文件则返回null
     * @throws Throwable
     *           FtpServerPathNotFoundException 服务器上的指定目录不存在
     */
    public List<FtpFile> getFileListInDir(String serverPath,String fileNameRegex) throws Throwable;

    /**
     * 下载文件
     * 下载FTP服务的指定目录下的某个文件到本地指定目录下
     * @param serverPath    FTP服务器上指定目录
     * @param serverFileName  要下载的文件名
     * @param localPath 本地指定目录 (如果该目录不存在，则自动创建)
     * @param localFileName 目录文件名
     * @return  已下载的文件
     * @throws Throwable
     *          FtpServerPathNotFoundException 服务器上的指定目录或文件不存在
     *          IllegalFilePathException 本地非法目录名异常
     *          FtpDownloadFileFailException 文件下载失败异常
     *          FtpDownLoadFileLengthError 下载的文件与服务器端文件大小不一致
     */
    public File downloadFile(String serverPath,String serverFileName,String localPath,String localFileName) throws Throwable;

    /**
     * 批量下载文件
     * 下载FTP服务器上指定目录下的所有文件，到本地指定目录下
     * @param serverPath FTP服务器上指定目录
     * @param localPath 本地指定目录 （本地目录或子目录不存在，则自动创建）
     * @return  已下载的文件列表
     * @throws Throwable
     *          FtpServerPathNotFoundException 服务器上的指定目录不存在
     *          IllegalFilePathException 本地非法目录名异常
     *          FtpDownloadFileFailException 文件下载失败异常
     *          FtpDownLoadFileLengthError 下载的文件与服务器端文件大小不一致
     */
    public List<File> downloadAllFile(String serverPath,String localPath) throws Throwable;

    /**
     * 批量下载文件
     * 下载FTP服务器上指定目录下的所有匹配文件，到本地指定目录
     * @param serverPath 服务器上指定目录
     * @param serverFileNameRegex  文件名匹配正则表达式
     * @param localPath 本地指定目录 （本地目录或子目录不存在，则自动创建）
     * @return 已下载的文件列表
     * @throws Throwable
     *          FtpServerPathNotFoundException 服务器上的指定目录不存在
     *          IllegalFilePathException 本地非法目录名异常
     *          FtpDownloadFileFailException 文件下载失败异常
     *          FtpDownLoadFileLengthError 下载的文件与服务器端文件大小不一致
     *
     */
    public List<File> downloadAllFile(String serverPath,String serverFileNameRegex ,String localPath) throws Throwable;

    /**
     *
     * 批量下载文件
     * 下载FTP服务器上指定目录下的所有匹配文件，到本地指定目录
     * @param serverPath 服务器上指定目录
     * @param serverFileNameRegex  文件名匹配正则表达式
     * @param localPath 本地指定目录 （本地目录或子目录不存在，则自动创建）
     * @param includeSubDir  是否下载子目录中的文件
     * @return 已下载的文件列表
     * @throws Throwable
     *          FtpServerPathNotFoundException 服务器上的指定目录不存在
     *          IllegalFilePathException 本地非法目录名异常
     *          FtpDownloadFileFailException 文件下载失败异常
     *          FtpDownLoadFileLengthError 下载的文件与服务器端文件大小不一致
     * @throws Throwable
     */
    public List<File> downloadAllFile(String serverPath,String serverFileNameRegex ,String localPath,boolean includeSubDir) throws Throwable;


    /**
     * 上传文件
     * 上传本地目录下的某个文件，到FTP服务器指定目录下，并以目标文件名存储
     * @param localPath 本地目录
     * @param localFileName 要上传的本地文件名
     * @param serverPath    目标目录
     * @param serverFileName 目标文件名
     * @return
     * @throws Throwable
     *           IllegalFilePathException 本地非法目录名
     *           FtpUploadFileFailException 上传文件失败
     *             FtpUploadFileLengthException 本地文件与上传后服务器端文件大小不符
     */
    public int uploadFile(String localPath, String localFileName,String serverPath, String serverFileName ) throws Throwable;

    /**
     * 批量上传文件
     * 上传本地目录下的所有匹配的文件到FTP指定目录下
     * @param localPath 本地目录
     * @param fileNameRegex  文件名匹配正则表达式
     * @param serverPath  目标目录  （目标目录或子目录不存在，则自动创建）
     * @param includeSubDir  是否上传子目录下的文件
     * @return
     * @throws Throwable
     *         IllegalFilePathException 本地非法目录名
     *         FtpUploadFileFailException 上传文件失败
     *         FtpCreateServerPathException FTP服务器上的目录创建失败
     *         FtpUploadFileLengthException 本地文件与上传后服务器端文件大小不符
     *
     */
    public int  uploadAllFile(String localPath, String fileNameRegex,String serverPath, boolean includeSubDir) throws Throwable;

    /**
     * 文件改名
     * 将FTP服务器上目标目录下的某个文件改名为目标文件名
     *
     * @param serverPath 目标目录
     * @param srcServerFileName 原文件名
     * @param destServerFileName 目标文件名
     * @return 改名的文件数
     * @throws Throwable
     *            FtpServerPathNotFoundException 服务器上的目标目录不存在
     *            FtpMoveFileException 服务器上的文件移动失败
     */
    public int renameServerFile(String serverPath, String srcServerFileName, String destServerFileName) throws Throwable;

    /**
     * 批量文件改名
     * 将FTP服务器上的目标目录下所有匹配的文件名改名，目标文件=原文件名+文件名后缀
     * @param serverPath 目标目录
     * @param srcFileNameRegex 文件名匹配正则表达式
     * @param destFileNameRegex 文件名后缀 （目标文件=原文件名+文件名后缀）
     * @param includeSubDir 是否对子目录下的文件改名
     * @return 改名的文件数
     * @throws Throwable
     *           FtpServerPathNotFoundException 服务器上的目标目录不存在
     *            FtpMoveFileException 服务器上的文件移动失败
     */
    public int renameAllServerFile (String serverPath,  String srcFileNameRegex, String destFileNameRegex,boolean includeSubDir) throws Throwable;

    /**
     * 移动FTP服务器上的指定目录下的某个文件 到目标目录下，以指定的文件名存储
     *
     * @param srcServerPath 原目录
     * @param srcServerFileName 源文件名
     * @param destServerPath  目标目录
     * @param destServerFileName    目标文件名
     * @return 移动的文件数
     * @throws Throwable
     *         FtpServerPathNotFoundException 服务器上的目标目录不存在
     *         FtpMoveFileException 服务器上的文件移动失败
     *          FtpCreateServerPathException 服务器上创建目录失败
     */
    public int moveServerFile(String srcServerPath, String srcServerFileName,String destServerPath,String destServerFileName) throws Throwable;

    /**
     * 批量文件移动
     * 移动FTP服务器上的某个目录下所有匹配的文件
     * @param serverPath 目标目录
     * @param fileNameRegex 文件名匹配正则表达式
     * @param destServerPath 目标目录 （目标目录或子目录不存在，则自动创建）
     * @param includeSubDir 是否移动子目录下的文件
     * @return 移动的文件数
     * @throws Throwable
     *         FtpServerPathNotFoundException 服务器上的目标目录不存在
     *         FtpMoveFileException 服务器上的文件移动失败
     *         FtpCreateServerPathException 服务器上创建目录失败
     */
    public int moveAllServerFile(String serverPath, String fileNameRegex, String destServerPath,boolean includeSubDir) throws Throwable;

    /**
     * 移动目录
     *
     * @param serverPath 原文件目录名
     * @param destServerPath 目标目录名 （目标目录或子目录不存在，则自动创建）
     * @return 移动的文件+目录总数
     * @throws Throwable
     *         FtpServerPathNotFoundException 服务器上的目录不存在
     *         FtpDeleteFileFailException   服务器上的目录删除失败
     *         FtpMoveFileException 服务器上的文件移动失败
     *         FtpCreateServerPathException 服务器上创建目录失败
     *
     */
    public int moveDir(String serverPath ,String destServerPath)  throws Throwable;

    /**
     * 删除FTP服务器上某个文件
     * @param serverPath 文件路径
     * @param serverFileName 文件名
     * @return 删除文件数
     * @throws Throwable
     *        FtpServerPathNotFoundException 服务器上的目录不存在
     *        FtpDeleteFileFailException   服务器上的目录删除失败
     */
    public int deleteFile(String serverPath,String serverFileName) throws Throwable;

    /**
     * 删除服务器上某个目录下的匹配文件(不删除子目录)
     *
     * @param serverPath 删除该目录下的文件
     * @param fileNameRegex 匹配文件名的正则表达式
     * @param includeSubDir 是否删除子目录下的文件
     * @return 删除文件数
     * @throws Throwable
     *        FtpServerPathNotFoundException 服务器上的目录不存在
     *        FtpDeleteFileFailException   服务器上的目录删除失败
     */
    public int  deleteAllFile(String serverPath,String fileNameRegex,boolean includeSubDir) throws Throwable;
    /**
     * 删除FTP服务器上目录
     * @param serverPath 文件目录
     * @return
     * @throws Throwable  异常
     *         FtpServerPathNotFoundException 服务器上的目录不存在
     *        FtpDeleteFileFailException   服务器上的目录删除失败
     *
     */
    public int  deleteDir(String serverPath ) throws Throwable;

    /**
     * 关闭FTP连接
     * @return
     */
    public boolean close();
}
