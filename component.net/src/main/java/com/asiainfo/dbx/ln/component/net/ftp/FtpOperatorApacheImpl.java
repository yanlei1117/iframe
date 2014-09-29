package com.asiainfo.dbx.ln.component.net.ftp;

import com.asiainfo.dbx.ln.component.exception.File.FileExceptionFactory;
import com.asiainfo.dbx.ln.component.exception.ftp.*;
import com.asiainfo.dbx.ln.component.net.proxy.NetProxy;
import com.asiainfo.dbx.ln.component.net.proxy.SocksProxy;
import com.asiainfo.dbx.ln.component.util.AppFileUtils;
import com.asiainfo.dbx.ln.component.util.AppStringUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * 代理情况
 * 1.支持socks代理,需要配置netProxy属性 == SocksProxy
 * 2.支持HTTP代理，需要配置netProxy属性 == HttpProxy
 *
 * 程序不能穿透本地防火墙，有防火墙情况下会报错
 *
 * Created by yanlei on 2014/8/15.
 */
@SuppressWarnings("unchecked")
public class FtpOperatorApacheImpl implements  FtpOperator{

    NetProxy netProxy;

    public NetProxy getNetProxy() {
        return netProxy;
    }

    public void setNetProxy(NetProxy netProxy) {
        this.netProxy = netProxy;
    }

    private static Logger logger = LoggerFactory.getLogger(FtpOperatorApacheImpl.class);
    FtpConfig ftpConfig = null;

    public FtpConfig getFtpConfig() {
        return ftpConfig;
    }

    public void setFtpConfig(FtpConfig ftpConfig) {
        this.ftpConfig = ftpConfig;
    }

    ThreadLocal<FTPClient> ftpClientThreadLocal = new ThreadLocal<FTPClient>()/*{

		@Override
		protected FTPClient initialValue() {
			// TODO Auto-generated method stub
			return loginFtp();
		}

	}*/;

    private FTPClient getFtpClent() throws Throwable{
        FTPClient ftpClient =  ftpClientThreadLocal.get();
        if(ftpClient == null){
            ftpClient = loginFtp();
            ftpClientThreadLocal.set(ftpClient);
        }
        return ftpClient;
    }
    private FTPClient getExistFtpClent() {
        FTPClient ftpClient =  ftpClientThreadLocal.get();

        return ftpClient;
    }
    private  FTPClient  loginFtp() throws Throwable{

        FTPClient ftp= new FTPClient();
        if(this.getNetProxy() != null){


            if(this.getNetProxy().getProxyType().equals(Proxy.Type.SOCKS)){
                ftp.setProxy(this.getNetProxy().createProxy());
                logger.info("apply socks proxy:{} ",this.getNetProxy());
            }else if(this.getNetProxy().getProxyType().equals(Proxy.Type.HTTP)){
                ftp =  new FTPHTTPClient(this.getNetProxy().getProxyHost(), Integer.parseInt(this.getNetProxy().getProxyPort()), this.getNetProxy().getUserName(), this.getNetProxy().getPassword());
            }else{
                logger.error ("only support socks and http proxy, invalid proxy type: {}",this.getNetProxy().toString());
            }

        }
        FTPClientConfig conf = new FTPClientConfig();
        if(this.getFtpConfig().getServerSystem() != null){
            conf= new FTPClientConfig(this.getFtpConfig().getServerSystem());
        }
        if(this.getFtpConfig().getServerLanguage() != null){
            conf.setServerLanguageCode(this.getFtpConfig().getServerLanguage());
        }

        if(this.getFtpConfig().getConnectTimeout()>0){
            ftp.setConnectTimeout(this.getFtpConfig().getConnectTimeout());

        }
        if(this.getFtpConfig().getDataTimeout()>0){
            ftp.setDefaultTimeout(this.getFtpConfig().getDataTimeout());
        }

        ftp.configure(conf);
        if(this.getFtpConfig().getServerEncoding() != null){
            ftp.setControlEncoding(this.getFtpConfig().getServerEncoding() );
        }else {
            ftp.setControlEncoding("GBK");
        }

        logger.info("connect ftp server {}:{}",this.getFtpConfig().getServerIp(),this.getFtpConfig().getServerPort());
        //ftp.connect(InetAddress.getByName(this.getFtpConfig().getServerIp()),this.getFtpConfig().getServerPort());
        ftp.connect( this.getFtpConfig().getServerIp(),this.getFtpConfig().getServerPort());

        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply))
        {
            ftp.disconnect();
            logger.info("connect ftp server {}:{},ReplyCode ={} error",this.getFtpConfig().getServerIp(),this.getFtpConfig().getServerPort(),reply);
            throw FtpExceptionFactory.createFtpConnectException(this.getFtpConfig().getServerIp(),this.getFtpConfig().getServerPort()+"",reply);
        }


        boolean loginFlag = ftp.login(this.getFtpConfig().getUsername(), this.getFtpConfig().getPassword());//登录
        if(!loginFlag) {
            logger.info("login ftp server:{}:{} use {}/{},result={}" , this.getFtpConfig().getServerIp(), this.getFtpConfig().getServerPort(), this.getFtpConfig().getUsername(), this.getFtpConfig().getPassword(), loginFlag);
            throw new FtpLoginError(this.getFtpConfig().getServerIp(),this.getFtpConfig().getServerPort()+"",this.getFtpConfig().getUsername(),this.getFtpConfig().getPassword(),loginFlag);
        }
         reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            logger.info("login ftp server:{}:{} use {}/{}, result=fail."+this.getFtpConfig().getServerIp(),this.getFtpConfig().getServerPort(),this.getFtpConfig().getUsername(),this.getFtpConfig().getPassword());
            ftp.disconnect();
            throw new FtpLoginError(this.getFtpConfig().getServerIp(),this.getFtpConfig().getServerPort()+"",this.getFtpConfig().getUsername(),this.getFtpConfig().getPassword(),loginFlag,false);
        }else{
            if(ftpConfig.isPassiveeMode()){
                ftp.enterLocalPassiveMode();
                logger.info("enterLocalPassiveMode");
            }else{
                ftp.enterLocalActiveMode();
                logger.info("enterLocalActiveMode");
            }
            if(ftpConfig.getBufferSize()>0){
                ftp.setBufferSize(ftpConfig.getBufferSize());
                logger.info("setBufferSize({})",ftpConfig.getBufferSize());
            }
            if(ftpConfig.getReceiveBufferSize() >0){
                ftp.setReceiveBufferSize(ftpConfig.getReceiveBufferSize());
                logger.info("setReceiveBufferSize({})",ftpConfig.getReceiveBufferSize());
            }
            if(ftpConfig.getSendBufferSize()>0){
                ftp.setSendBufferSize(ftpConfig.getSendBufferSize());
                logger.info("setSendBufferSize({})",ftpConfig.getSendBufferSize());
            }
            if(ftpConfig.getReceieveDataSocketBufferSize()>0){
                ftp.setReceieveDataSocketBufferSize(ftpConfig.getReceieveDataSocketBufferSize());
                logger.info("setReceieveDataSocketBufferSize({})",ftpConfig.getReceieveDataSocketBufferSize());
            }
            if(ftpConfig.getSendDataSocketBufferSize()>0){
                ftp.setSendDataSocketBufferSize(ftpConfig.getSendDataSocketBufferSize());
                logger.info("setSendDataSocketBufferSize({})",ftpConfig.getSendDataSocketBufferSize());
            }
            if(this.getFtpConfig().getSoTimeout()>0){
                ftp.setSoTimeout(this.getFtpConfig().getSoTimeout());
            }
            if(this.getFtpConfig().getDataTimeout()>0){
                ftp.setDataTimeout(this.getFtpConfig().getDataTimeout());
            }

            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.setKeepAlive(true);
            ftp.setConnectTimeout(10*1000);
            ftp.setControlKeepAliveTimeout(10*1000);
            ftp.setControlKeepAliveReplyTimeout(10*1000);
            logger.info("login ftp server:{}:{} use {}/{} ,result=success,",this.getFtpConfig().getServerIp(),this.getFtpConfig().getServerPort(),this.getFtpConfig().getUsername(),this.getFtpConfig().getPassword());
        }

        return ftp;


    }
    private FtpFile convertToFtpFile(String serverPath,FTPFile srcFtpFile){
        if(srcFtpFile == null){
            return null;
        }else{
            return new FtpFileApache(serverPath,srcFtpFile);
        }

    }


    public List<FtpFile> getFileListInDir(String serverPath) throws Throwable{
        return getFileListInDir(serverPath,null);

    }
    public FtpFile getFile (String serverPath,final String fileName) throws Throwable{
       List<FtpFile> ftpFileList = getFileListInDir(serverPath,fileName);
       if(ftpFileList!= null && ftpFileList.size()>0){
           return ftpFileList.get(0);
       }else{
           return null;
       }
    }
    @Override
    public List<FtpFile> getFileListInDir(String serverPath,final String fileNameRegex) throws Throwable {

        if(getFtpClent() != null && cdPath(serverPath)){
            FTPFile[] ftpFiles = null;
            if(fileNameRegex != null) {
                ftpFiles = getFtpClent().listFiles(serverPath, new FTPFileFilter() {
                    @Override
                    public boolean accept(FTPFile file) {
                        if(file != null) {
                            boolean match = AppStringUtils.matchPartString(fileNameRegex, file.getName());
                            return match;
                        }else{
                            return false;
                        }
                    }
                });
            }else{
                ftpFiles = getFtpClent().listFiles(serverPath,new FTPFileFilter() {
                    @Override
                    public boolean accept(FTPFile file) {
                        if(file != null) {
                            String fileName = file.getName();
                            return (!fileName.equals(".") && !fileName.equals(".."));
                        }else{
                            return false;
                        }
                    }
                });
            }
            if(AppValidationUtils.notEmpty(ftpFiles)){
                List<FtpFile> ftpFileList = new ArrayList(ftpFiles.length);
                for(FTPFile srcFtpFile :ftpFiles){
                    FtpFile ftpFile =  convertToFtpFile(serverPath,srcFtpFile);
                    ftpFileList.add(ftpFile);
                }
                return ftpFileList;
            }
        }
        return null;
    }

    public long getFileLength(String serverPath,final String fileName) throws Throwable{
        if(getFtpClent() != null && cdPath(serverPath)){//&&
            //return getFtpClent().listFiles();
            final AtomicLong   totalSize = new AtomicLong(0);

            getFtpClent().listFiles(serverPath+this.getFtpConfig().getServerPathSpliter()+fileName,new FTPFileFilter(){
                @Override
                public boolean accept(FTPFile file) {
                    if(file != null) {
                        totalSize.addAndGet(file.getSize());
                    }
                    return false;
                }
            });
            return totalSize.longValue();
        }
            return -1;

    }




    private boolean cdPath(String serverPath) throws Throwable{
        boolean changeDirFlag =	getFtpClent().changeWorkingDirectory(serverPath);
        if(changeDirFlag){
            logger.info("command:\" cd "+serverPath+"\" success");
            return  true;
        }else{
            logger.info("command:\" cd "+serverPath+"\" fail");
            throw FtpExceptionFactory.createFtpServerPathNotFoundException(serverPath);
        }
    }

    public int  deleteFile(String serverPath,String serverFileName) throws Throwable{
        if(getFtpClent() != null && cdPath(serverPath)){
            boolean result =  getFtpClent().deleteFile(serverFileName);
            logger.info("deleteFile: serverPath:{}  fileName:{}  result={}",serverPath,serverFileName,result==true?"OK":"FAIL");
            if(!result){
                throw FtpExceptionFactory.createFtpDeleteFileFailException(serverPath+this.getFtpConfig().getServerPathSpliter()+serverFileName);
            }
        }
        return 1;
    }

    private String getPath(String serverPath){
        if(serverPath.endsWith(this.getFtpConfig().getServerPathSpliter())){
            serverPath = serverPath.substring(0,serverPath.length()-1);
        }
        int index = serverPath.lastIndexOf(this.getFtpConfig().getServerPathSpliter());
        if(index >0 && serverPath.length()>index+1){
            return serverPath.substring(index+1);
        }else{
            return  null;
        }
    }
    /**
     * 删除FTP服务器上目录
     * @param remoteDir
     * @return
     * @throws FtpServerPathNotFoundException 服务器上的目录不存在
     *         FtpDeleteFileFailException   服务器上的目录删除失败
     *         Throwable  其它异常
     */

    public int  deleteDir(String remoteDir ) throws Throwable{
        logger.info("deleteDir: remoteDir={}, START.......",remoteDir);
        int totalDeleteFileNum = 0;
        if(getFtpClent() != null && cdPath(remoteDir)){

            List<FtpFile> filelist = this.getFileListInDir(remoteDir);
            if(filelist != null && filelist.size()>0){
                for(FtpFile ftpFile :filelist){
                    String fileName = ftpFile.getName();
                    if(ftpFile.isDirectory()){
                        totalDeleteFileNum += deleteDir(remoteDir+this.getFtpConfig().getServerPathSpliter()+fileName);

                    }else{
                        totalDeleteFileNum+= deleteFile(remoteDir,ftpFile.getName());
                    }
                }
            }
            String parentPath = this.getParentFilePath(remoteDir);
            if(parentPath != null){
                this.cdPath(parentPath);
                String currentPath = this.getPath(remoteDir);
                if(currentPath != null){
                    boolean flag=  this.getFtpClent().removeDirectory(remoteDir);
                    if(!flag){
                        throw FtpExceptionFactory.createFtpDeleteFileFailException(remoteDir);
                    }
                }
            }



            totalDeleteFileNum++;
        }
        logger.info("deleteDir: serverDir={}, Finish.......result=OK.",remoteDir);
        return totalDeleteFileNum;
    }

    /**
     *
     * @param serverPath
     * @param fileNameRegex
     * @param includeSubDir
     * @return
     * @throws Throwable
     */

    public int  deleteAllFile(String serverPath,String fileNameRegex,boolean includeSubDir) throws Throwable{
        logger.info("deleteAllFile: serverPath={}, fileNameRegex={},includeSubDir={}, START.......",serverPath,fileNameRegex,includeSubDir);
        int totalDeleteFileNum = 0;
        if(getFtpClent() != null && cdPath(serverPath)){

           List<FtpFile> filelist = this.getFileListInDir(serverPath,fileNameRegex);
            if(filelist != null && filelist.size()>0){
                for(FtpFile ftpFile :filelist){
                    String fileName = ftpFile.getName();
                        if(ftpFile.isDirectory()){
                            totalDeleteFileNum += deleteAllFile(serverPath+this.getFtpConfig().getServerPathSpliter()+fileName,fileNameRegex,includeSubDir);

                        }else{
                            totalDeleteFileNum+= deleteFile(serverPath,ftpFile.getName());

                        }
                }
            }


        }
        logger.info("deleteAllFile: serverPath={}, fileNameRegex={},includeSubDir={}, FINISH.....result=OK.",serverPath,fileNameRegex,includeSubDir);
        return totalDeleteFileNum;
    }

    private  File downloadFile(String serverPath, String serverFileName,String localPath, String localFileName,long serverFileLength) throws Throwable{
        if(getFtpClent() != null && cdPath(serverPath)){
            OutputStream os = null;
            AppFileUtils.createLocalPathIfNotExist(new File(localPath));
            File localFile = new File(localPath,localFileName);
            if(localFile.exists() && localFile.length() == serverFileLength){
                logger.info("ftp download file {}:{},serverFile:{}{}{} to localFile {},but localFile exist, and lengh{} equal server file, download ignore.",this.getFtpConfig().getServerIp(),this.getFtpConfig().getServerPort(),serverPath,this.getFtpConfig().getServerPathSpliter(),serverFileName,localFile.getAbsoluteFile(), localFile.length());
                return localFile;
            }
            try {
                logger.info("ftp download file {}:{},serverFile:{}{}{} to localFile {}, fileLength:{},to start..........",this.getFtpConfig().getServerIp(),this.getFtpConfig().getServerPort(),serverPath,this.getFtpConfig().getServerPathSpliter(),serverFileName,localFile.getAbsoluteFile(),serverFileLength);
                boolean flag = true;
                if(serverFileLength>0) {
                    os = new FileOutputStream(localFile);
                    flag = getFtpClent().retrieveFile(serverFileName, os);
                }else{
                    flag = localFile.createNewFile();
                }
                if(flag) {
                    if (serverFileLength != localFile.length()) {
                        FtpExceptionFactory.createFtpDownLoadFileLengthError(serverPath + this.getFtpConfig().getServerPathSpliter() + serverFileName, serverFileLength, localFile.getAbsolutePath(), localFile.length());
                    }else{
                        logger.info("ftp download file {}:{},serverFile:{}{}{} to localFile {},finish.........,length equal {}= {}, result = OK.",this.getFtpConfig().getServerIp(),this.getFtpConfig().getServerPort(),serverPath,this.getFtpConfig().getServerPathSpliter(),serverFileName,localFile.getAbsoluteFile(),serverFileLength, localFile.length());
                    }
                    return localFile;
                }else{
                    logger.info("ftp download file {}:{},serverFile:{}{}{} to localFile {},finish.........result = {}",this.getFtpConfig().getServerIp(),this.getFtpConfig().getServerPort(),serverPath,this.getFtpConfig().getServerPathSpliter(),serverFileName,localFile.getAbsoluteFile(),flag);
                    throw new FtpDownloadFileFailException(serverPath+this.getFtpConfig().getServerPathSpliter()+serverFileName,localFile.getAbsolutePath());
                }
            }catch (Throwable e){
                logger.info("ftp download file {}:{},serverFile:{}{}{} to localFile {} error:{}",this.getFtpConfig().getServerIp(),this.getFtpConfig().getServerPort(),serverPath,this.getFtpConfig().getServerPathSpliter(),serverFileName,localFile.getAbsoluteFile(),e);
                throw e;
            }finally {
                if(os != null){
                    os.close();;
                }
            }
        }
        return null;
    }
    public File downloadFile(String serverPath, String serverFileName,String localPath, String localFileName) throws Throwable{
        long  fileLength = this.getFileLength(serverPath,serverFileName);
        if(fileLength == -1){
            throw FtpExceptionFactory.createFtpServerPathNotFoundException(serverPath+this.getFtpConfig().getServerPathSpliter()+serverFileName);
        }
        return downloadFile(serverPath,serverFileName,localPath,localFileName,fileLength);

    }



    public List<File> downloadAllFile(String serverPath, String localPath) throws Throwable{
        // TODO Auto-generated method stub
       return  downloadAllFile(serverPath,null,localPath);

    }

    public List<File> downloadAllFile(String serverPath,String serverFileNameRegex ,String localPath,boolean includeSubDir) throws Throwable{
        logger.info(" downloadAllFile :  from {} to {},by fileNameFilter:{},start ............",serverPath,localPath,serverFileNameRegex);
        if(getFtpClent() != null && cdPath(serverPath)){
            List<File> fileList = new ArrayList();
            List<FtpFile> ftpFileList  = this.getFileListInDir(serverPath,serverFileNameRegex);
            if(ftpFileList != null && ftpFileList.size()>0){

                File localDir = new File(localPath);
                logger.debug("validate  file path named '{}'  exist?, if not ,to create ", localDir.getAbsolutePath());
                AppFileUtils.createLocalPathIfNotExist(localDir);

                for(FtpFile ftpFile:ftpFileList){
                    String fileName = ftpFile.getName();
                    if(!fileName.equals(".") && !fileName.equals("..")){
                        if(ftpFile.isDirectory() ){
                            if( includeSubDir) {
                                List<File> dirFileList = downloadAllFile(serverPath + this.getFtpConfig().getServerPathSpliter() + ftpFile.getName(), new File(localPath, ftpFile.getName()).getAbsolutePath());
                                if (dirFileList != null) {
                                    fileList.addAll(dirFileList);
                                }
                            }
                        }else{
                            File file = downloadFile(serverPath, ftpFile.getName(), localPath, ftpFile.getName(),ftpFile.getSize());
                            if(file == null){
                                throw FtpExceptionFactory.createFtpDownloadFileError(serverPath + this.getFtpConfig().getServerPathSpliter() + ftpFile.getName(), new File(localPath, ftpFile.getName()).getAbsolutePath());
                            }else{
                                fileList.add(file);
                            }
                        }

                    }
                }
                logger.info(" downloadAllFile :  from {} to {},by fileNameFilter:{},OK, total files num:{}",serverPath,localPath,serverFileNameRegex,fileList.size());
                return fileList;
            }else{
                logger.info(" downloadAllFile :  from {} to {},by fileNameFilter:{},OK, total files num:{}",serverPath,localPath,serverFileNameRegex,"0");
            }

        }
        return Collections.emptyList();
    }
    public List<File> downloadAllFile(String serverPath,String serverFileNameRegex ,String localPath) throws Throwable{
        return this.downloadAllFile(serverPath,serverFileNameRegex,localPath,false);
    }



    @Override

    public int  moveServerFile(String srcServerPath,String srcServerFileName, String destServerPath,  String destServerFileName) throws Throwable {
        if(getFtpClent() != null){
            String src = srcServerPath+this.getFtpConfig().getServerPathSpliter()+srcServerFileName;
            String dest = destServerPath+this.getFtpConfig().getServerPathSpliter()+destServerFileName;
            boolean  flag =getFtpClent().rename(src, dest);
            logger.info("move server file {} to {} ,result={}",src,dest,flag==true?"OK":"FAIL");
            if(!flag){
                throw FtpExceptionFactory.createFtpMoveFileException(srcServerPath,srcServerFileName,destServerPath,destServerFileName);
            }

        }
        return 1;
    }

    @Override
    public int  renameServerFile (String serverPath, String srcServerFileName, String destServerFileName) throws Throwable {
        String destFileName = destServerFileName.replace("${srcFileName}",srcServerFileName);
        if(getFtpClent() != null && cdPath(serverPath)){
            String src = serverPath+this.getFtpConfig().getServerPathSpliter()+srcServerFileName;
            String dest = serverPath+this.getFtpConfig().getServerPathSpliter()+destFileName;
            boolean flag = getFtpClent().rename(src,dest);
            logger.info("rename server file {} to {} under path '{}',result={}",srcServerFileName,destFileName,serverPath,flag==true?"OK":"FAIL");
            if(flag){
                return 1;
            }else{
                return 0;
            }
        }
        return 1;
    }
    @Override

    public int renameAllServerFile (String serverPath,  String srcFileNameRegex, String destFileNameRegex,boolean includeSubDir) throws Throwable {
        int renameTotalFilesNum =0;
        List<FtpFile> ftpFileList = this.getFileListInDir(serverPath,srcFileNameRegex);
        if(ftpFileList != null && ftpFileList.size()>0) {
            for (FtpFile ftpFile : ftpFileList) {
                if (ftpFile.isDirectory()&&includeSubDir) {
                    renameTotalFilesNum +=  renameAllServerFile(serverPath + this.getFtpConfig().getServerPathSpliter() + ftpFile.getName(), srcFileNameRegex, destFileNameRegex,includeSubDir);

                } else if (ftpFile.isFile() && destFileNameRegex != null) {

                    renameTotalFilesNum+=  renameServerFile(serverPath, ftpFile.getName(),  destFileNameRegex);

                }
            }
        }
        return renameTotalFilesNum;
    }

    @Override
    public int moveAllServerFile(String serverPath, String fileNameRegex, String destServerPath,boolean includeSubDir) throws Throwable {
        int moveTotalFilesNum =0;
        List<FtpFile> ftpFileList = this.getFileListInDir(serverPath,fileNameRegex);
       if(ftpFileList != null && ftpFileList.size()>0) {
           createServerDirIfNotExist(destServerPath);
           for (FtpFile ftpFile : ftpFileList) {
               if (ftpFile.isDirectory() && includeSubDir) {
                   moveTotalFilesNum+=  moveAllServerFile(serverPath + this.getFtpConfig().getServerPathSpliter() + ftpFile.getName(), fileNameRegex, destServerPath + this.getFtpConfig().getServerPathSpliter() + ftpFile.getName(),includeSubDir);
               } else if (ftpFile.isFile()) {
                   moveTotalFilesNum+=  moveServerFile(serverPath, ftpFile.getName(), destServerPath, ftpFile.getName());
               }
           }
       }
        return moveTotalFilesNum;
    }


    public int moveDir(String serverPath ,String destServerPath)  throws Throwable{
        int moveTotalFilesNum =0;
        createServerDirIfNotExist(destServerPath);
        List<FtpFile> ftpFileList = this.getFileListInDir(serverPath);
        if(ftpFileList != null && ftpFileList.size()>0) {
            for (FtpFile ftpFile : ftpFileList) {
                if (ftpFile.isDirectory()) {
                    moveTotalFilesNum+=  moveDir(serverPath + this.getFtpConfig().getServerPathSpliter() + ftpFile.getName(), destServerPath + this.getFtpConfig().getServerPathSpliter() + ftpFile.getName());
                    moveTotalFilesNum++;
                } else if (ftpFile.isFile()) {
                    moveTotalFilesNum+=  moveServerFile(serverPath, ftpFile.getName(), destServerPath, ftpFile.getName());
                }
            }
        }
        boolean flag = this.getFtpClent().removeDirectory(serverPath);
        if(!flag){
            throw FtpExceptionFactory.createFtpDeleteFileFailException(serverPath);
        }
        return moveTotalFilesNum;
    }


    public int uploadFile(String localPath, String localFileName,String serverPath, String serverFileName ) throws Throwable{
        // TODO Auto-generated method stub
        this.createServerDirIfNotExist(serverPath);
        if(getFtpClent() != null&& cdPath(serverPath)){
            File localFile = new File(localPath,localFileName);
            if(!localFile.exists()){
                throw FileExceptionFactory.createIllegalFilePathException(localFile.getAbsolutePath());
            }
            InputStream is  = new FileInputStream(localFile);
            boolean flag = getFtpClent().storeFile(serverFileName, is);
            logger.info("uploadFile  file {} to {}:{}  {},result= {}",localFile.getAbsolutePath(),this.getFtpConfig().getServerIp(),this.getFtpConfig().getServerPort(),serverPath,flag);
            is.close();
            long remoteFileLength = this.getFileLength(serverPath,serverFileName);
            if(remoteFileLength != localFile.length()){
                throw FtpExceptionFactory.createFtpUploadFileLengthException(serverPath+this.getFtpConfig().getServerPathSpliter()+serverFileName,remoteFileLength,localFile.getAbsolutePath(),localFile.length());
            }
            if(!flag){
                throw FtpExceptionFactory.createFtpUploadFileFailException(localFile.getAbsolutePath(),serverPath+this.getFtpConfig().getServerPathSpliter()+serverFileName);
            }
        }
        return 1;
    }

    public int  uploadAllFile(String localPath,final String fileNameRegex,String serverPath, boolean includeSubDir) throws Throwable{
        // TODO Auto-generated method stub
        int totalFilesNum = 0;
        if(getFtpClent() != null  ){
            File dirFile  = new File(localPath);
            if(!dirFile.exists()){
                throw FileExceptionFactory.createIllegalFilePathException(dirFile.getAbsolutePath());
            }else {
                logger.info("upload all File from {} to {},by fileNameRegex:{} start .....",dirFile.getAbsolutePath(),serverPath,fileNameRegex);
                File files [] = dirFile.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        if(fileNameRegex == null){
                            if( name.equals(".")|| name.equals("..")) {
                                return false;
                            }else {
                                return true;
                            }
                        }else{
                            return AppStringUtils.matchPartString(fileNameRegex, name);
                        }
                    }
                });
                for(int i=0;i<files.length;i++){
                    if( files[i].isDirectory() && includeSubDir){
                        totalFilesNum +=uploadAllFile(files[i].getAbsolutePath(),fileNameRegex,serverPath+this.getFtpConfig().getServerPathSpliter()+files[i].getName(),includeSubDir);
                    }else if( files[i].isFile()){
                        String fileName = files[i].getName();
                        uploadFile(localPath,fileName,serverPath,fileName);
                        totalFilesNum++;
                    }

                }
                logger.info("upload all File from {} to {},by fileNameRegex:{} OK. total file num:{}",dirFile.getAbsolutePath(),serverPath,fileNameRegex,totalFilesNum);
            }
        }
        return totalFilesNum;
    }

    private String getParentFilePath(String serverPath){
        if(serverPath.equals(this.getFtpConfig().getServerPathSpliter())){
            return null;
        }
        if(serverPath.endsWith(this.getFtpConfig().getServerPathSpliter())){
            serverPath = serverPath.substring(0,serverPath.length()-1);
        }
        int index = serverPath.lastIndexOf(this.getFtpConfig().getServerPathSpliter());
        if(index != -1 && index != 0){
            return serverPath.substring(0,index);
        }else if(index ==0){
            return this.getFtpConfig().getServerPathSpliter();
        }else{
            return null;
        }
    }
    public void  createServerDirIfNotExist(String serverPath) throws Throwable{
        if(!this.getFtpClent().changeWorkingDirectory(serverPath)){
            boolean flag = true;
            String parentFilePath = getParentFilePath(serverPath);
            if(parentFilePath == null ){
                flag =  false;
            }else {
                 createServerDirIfNotExist(parentFilePath);
                 flag = this.getFtpClent().makeDirectory(serverPath);
                logger.info(" make directory '{}'  ,result = {}",serverPath,flag==true?"OK":"FAIL");

            }
            if(!flag){
                throw FtpExceptionFactory.createFtpCreateServerPathException(serverPath);
            }

        }
    }




    public boolean close() {
        // TODO Auto-generated method stub
        try{

            if(getExistFtpClent() != null){
                boolean flag = getExistFtpClent().logout();
                logger.info("logout from  ftp server {}:{} ,result={}",this.getFtpConfig().getServerIp(),this.getFtpConfig().getServerPort(),flag == true?"OK":"FAIL");
            }

        }catch(Throwable e){
            logger.error("",e);
        }finally{
            try{
                if(getExistFtpClent() != null){
                    if (getExistFtpClent().isConnected()) {
                        try {
                            getExistFtpClent().disconnect();
                            logger.info("disconnect from  ftp server {}:{} ",this.getFtpConfig().getServerIp(),this.getFtpConfig().getServerPort());
                        } catch(IOException ioe) {
                            // do nothing
                        }

                    }
                }
            }catch(Throwable e){
                logger.error("",e);
            }
            this.ftpClientThreadLocal.set(null);
        }
        return true;
    }


}
