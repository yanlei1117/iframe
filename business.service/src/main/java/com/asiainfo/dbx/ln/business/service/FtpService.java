package com.asiainfo.dbx.ln.business.service;

import com.asiainfo.dbx.ln.component.exception.ftp.FtpServerPathNotFoundException;
import com.asiainfo.dbx.ln.component.net.ftp.FtpFile;
import com.asiainfo.dbx.ln.component.net.ftp.FtpOperator;
import com.asiainfo.dbx.ln.component.net.proxy.NetProxy;
import com.asiainfo.dbx.ln.component.spring.impl.ApplicationContextHolderParentFixed;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.File;
import java.net.InetAddress;
import java.util.List;

/**
 * Created by yanlei on 2014/8/19.
 */
public class FtpService {

   private String taskName = null;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    private AlarmService alarmService;

    public AlarmService getAlarmService() {
        return alarmService;
    }

    public void setAlarmService(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    private static Logger logger = LoggerFactory.getLogger(FtpService.class);

    private NetProxy netProxy;

    public NetProxy getNetProxy() {
        return netProxy;
    }

    public void setNetProxy(NetProxy netProxy) {
        this.netProxy = netProxy;
    }

    private FtpOperator ftpOperator;

    public FtpOperator getFtpOperator() {
        return ftpOperator;
    }




    public void setFtpOperator(FtpOperator ftpOperator) {
        this.ftpOperator = ftpOperator;
    }

    int retryTimes = 1;

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    private String serverFilePath ;

    private String serverFileNameRegex;
    private String localFilePath;

    private String renameFileSubfix;

    public String getServerFilePath() {
        try{
            return (String)AppVarUtils.getVarContainer(FtpService.class).getVar(serverFilePath);
        }catch(Throwable e){
            logger.error("",e);
        }
        return serverFilePath;
    }

    public void setServerFilePath(String serverFilePath) {
        this.serverFilePath = serverFilePath;
    }

    public String getServerFileNameRegex() {
        return serverFileNameRegex;
    }

    public void setServerFileNameRegex(String serverFileNameRegex) {
        this.serverFileNameRegex = serverFileNameRegex;
    }

    public String getLocalFilePath() {
        try{
            return (String)AppVarUtils.getVarContainer(FtpService.class).getVar(localFilePath);
        }catch(Throwable e){
            logger.error("",e);
        }
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    public String getRenameFileSubfix() {
        return renameFileSubfix;
    }

    public void setRenameFileSubfix(String renameFileSubfix) {
        this.renameFileSubfix = renameFileSubfix;
    }





    volatile boolean netProxyFlag = false;
    public void startDownloadFile(){
         logger.info("");
        logger.info("");
        logger.info("");
        logger.info("start to run task:{}",this.getTaskName());

            try {
                if (this.getNetProxy() != null) {
                    if (!netProxyFlag) {
                        this.getNetProxy().applyProxy();
                        netProxyFlag = !netProxyFlag;
                    }
                }
            }catch(Throwable e){
                logger.error(" apply net proxy error, to exit",e);
                System.exit(0);
            }
            int currentErrorTime = 0;
            while(currentErrorTime<retryTimes){
                try {
                    if(currentErrorTime>0){
                        logger.info(" downloadFile occur error,current retry time () less than {},to download again",currentErrorTime,retryTimes);
                    }
                    downloadFile();

                    break;
                }catch(Throwable e){
                    currentErrorTime++;
                    logger.error("",e);
                    try {
                        logger.info("sleep 10000 ms");
                        Thread.currentThread().sleep(10000);
                    }catch(Exception ee){

                    }
                    continue;
                }finally {
                    this.getFtpOperator().close();
                    logger.info("");
                    logger.info("");
                }

            }
            if(currentErrorTime== retryTimes){
                logger.error(" download file occur error times {} == retryTimes({}),to exit:",currentErrorTime,retryTimes);
                this.getAlarmService().alarm(this.getTaskName()+":下载失败");
            }

    }

    public void downloadFile() throws Throwable{
        logger.info("config message:{}",this);
        logger.info("Start to download file ....................");
        try {
            List<File> fileList = this.getFtpOperator().downloadAllFile(this.getServerFilePath(), this.getServerFileNameRegex(), this.getLocalFilePath());
            if (fileList != null && fileList.size() > 0 && this.getRenameFileSubfix() != null) {
                this.getFtpOperator().renameAllServerFile(this.getServerFilePath(), this.getServerFileNameRegex(), this.getRenameFileSubfix(), false);
            }
        }catch(FtpServerPathNotFoundException e){
            logger.error(e.getMessage());
            this.getAlarmService().alarm(this.getTaskName()+":"+e.getMessage());
        }
        logger.info("End to download file ....................");
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FtpService{");

        sb.append(", serverFilePath='").append(getServerFilePath()).append('\'');

        sb.append(", serverFileNameRegex='").append(serverFileNameRegex).append('\'');
        sb.append(", localFilePath='").append(getLocalFilePath()).append('\'');

        sb.append(", retryTimes=").append(retryTimes);
        sb.append("renameFileSubfix='").append(renameFileSubfix).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static  void main(String args []){
       // FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("file_config.xml");

        try {

            ApplicationContextHolderParentFixed applicationContextHolder = new ApplicationContextHolderParentFixed();
            applicationContextHolder.instanceApplicationContext("ftp", "file:ftp_config.xml");
            SocketServer socketServer =  (SocketServer)applicationContextHolder.getApplicationContext().getBean("socketServer");
            socketServer.startPort();
            Object obj = new Object();
            synchronized (obj) {
                obj.wait();
            }
        }catch(Throwable e){
            logger.error("",e);
        }
    }
}
