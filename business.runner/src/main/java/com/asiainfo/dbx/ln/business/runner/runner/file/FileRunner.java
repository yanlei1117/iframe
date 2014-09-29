package com.asiainfo.dbx.ln.business.runner.runner.file;

import com.asiainfo.dbx.ln.business.runner.Runner;

/**
 * Created by yanlei on 2014/9/23.
 */
public abstract  class FileRunner extends Runner {
    String localPath;
    String localFileNameRegex;

    String defineName;
    String destFileNameRegex;
    String destLocalPath;
    String zipFilePath;
    String zipFileName;

    String destDirName;

    public String getDestDirName() {
        return destDirName;
    }

    public void setDestDirName(String destDirName) {
        this.destDirName = destDirName;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getLocalFileNameRegex() {
        return localFileNameRegex;
    }

    public void setLocalFileNameRegex(String localFileNameRegex) {
        this.localFileNameRegex = localFileNameRegex;
    }

    public String getDefineName() {
        return defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    public String getDestFileNameRegex() {
        return destFileNameRegex;
    }

    public void setDestFileNameRegex(String destFileNameRegex) {
        this.destFileNameRegex = destFileNameRegex;
    }

    public String getDestLocalPath() {
        return destLocalPath;
    }

    public void setDestLocalPath(String destLocalPath) {
        this.destLocalPath = destLocalPath;
    }

    public String getZipFilePath() {
        return zipFilePath;
    }

    public void setZipFilePath(String zipFilePath) {
        this.zipFilePath = zipFilePath;
    }

    public String getZipFileName() {
        return zipFileName;
    }

    public void setZipFileName(String zipFileName) {
        this.zipFileName = zipFileName;
    }
}
