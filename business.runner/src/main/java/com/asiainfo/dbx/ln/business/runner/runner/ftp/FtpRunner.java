package com.asiainfo.dbx.ln.business.runner.runner.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;

/**
 * Created by yanlei on 2014/9/23.
 */
public abstract  class FtpRunner extends Runner {



    String serverId;
    String serverPath;
    String serverFileNameRegex;
    String serverFileName;
    String localPath;
    String defineName;
    String localFileName;
    String localFileNameRegex;

    String srcServerPath;

    String srcServerFileName;
    String destServerPath;
    String destServerFileName;



    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public String getServerFileNameRegex() {
        return serverFileNameRegex;
    }

    public void setServerFileNameRegex(String serverFileNameRegex) {
        this.serverFileNameRegex = serverFileNameRegex;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }
    public String getDefineName() {
        return defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getLocalFileName() {
        return localFileName;
    }

    public void setLocalFileName(String localFileName) {
        this.localFileName = localFileName;
    }

    public String getSrcServerPath() {
        return srcServerPath;
    }

    public void setSrcServerPath(String srcServerPath) {
        this.srcServerPath = srcServerPath;
    }

    public String getSrcServerFileName() {
        return srcServerFileName;
    }

    public void setSrcServerFileName(String srcServerFileName) {
        this.srcServerFileName = srcServerFileName;
    }

    public String getDestServerPath() {
        return destServerPath;
    }

    public void setDestServerPath(String destServerPath) {
        this.destServerPath = destServerPath;
    }

    public String getDestServerFileName() {
        return destServerFileName;
    }

    public void setDestServerFileName(String destServerFileName) {
        this.destServerFileName = destServerFileName;
    }

    public String getLocalFileNameRegex() {
        return localFileNameRegex;
    }

    public void setLocalFileNameRegex(String localFileNameRegex) {
        this.localFileNameRegex = localFileNameRegex;
    }
}
