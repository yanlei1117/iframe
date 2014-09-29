package com.asiainfo.dbx.ln.component.net.ftp;

import org.apache.commons.net.ftp.FTPFile;

import java.util.Date;

/**
 * Created by yanlei on 2014/8/18.
 */
public class FtpFileApache implements   FtpFile{
    FTPFile ftpFile = null;
    String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public FtpFileApache(String filePath,FTPFile ftpFile){
        this.ftpFile = ftpFile;
        this.filePath = filePath;
    }
    @Override
    public boolean isDirectory() {
        return  this.ftpFile.isDirectory();
    }

    @Override
    public boolean isFile() {
        return  this.ftpFile.isFile();
    }

    @Override
    public long getSize() {
        return  this.ftpFile.getSize();
    }

    @Override
    public String getName() {
        return  this.ftpFile.getName();
    }

    @Override
    public Date getTimestamp() {
        return  this.ftpFile.getTimestamp().getTime();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FtpFileApache{");
        sb.append("name=").append(getName());
        sb.append('}');
        return sb.toString();
    }
}
