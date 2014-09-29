package com.asiainfo.dbx.ln.business.runner.runner.file;

import com.asiainfo.dbx.ln.component.util.AppFileUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/23.
 */
public class FileZipRunner extends  FileRunner {
    @Override
    public void run() throws Throwable {
        String varLocalPath = (String)AppVarUtils.getVarContainer(FileZipRunner.class).getVar(this.getLocalPath());
        String varLocalFileNameRegex = (String)AppVarUtils.getVarContainer(FileZipRunner.class).getVar(this.getLocalFileNameRegex());
        String varZipFilePath = (String)AppVarUtils.getVarContainer(FileZipRunner.class).getVar(this.getZipFilePath());
        String varZipFileName = (String)AppVarUtils.getVarContainer(FileZipRunner.class).getVar(this.getZipFileName());
        AppFileUtils.getFileService(FileZipRunner.class).zipFile(varLocalPath, varLocalFileNameRegex, varZipFilePath,varZipFileName);

    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nFileZipRunner{");
        sb.append("localFileNameRegex='").append(this.getLocalFileNameRegex()).append('\'');
        sb.append("zipFilePath='").append(this.getZipFilePath()).append('\'');
        sb.append("zipFileName='").append(this.getZipFileName()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
