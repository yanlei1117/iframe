package com.asiainfo.dbx.ln.business.runner.runner.file;

import com.asiainfo.dbx.ln.component.util.AppFileUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/23.
 */
public class FileUnZipRunner extends  FileRunner {
    @Override
    public void run() throws Throwable {
        String varLocalPath = (String)AppVarUtils.getVarContainer(FileUnZipRunner.class).getVar(this.getLocalPath());
        String varDestLocalPath = (String)AppVarUtils.getVarContainer(FileUnZipRunner.class).getVar(this.getDestLocalPath());
        String varZipFileName = (String)AppVarUtils.getVarContainer(FileUnZipRunner.class).getVar(this.getZipFileName());
        AppFileUtils.getFileService(FileUnZipRunner.class).unZipFile(varLocalPath, varZipFileName, varDestLocalPath);
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nFileUnZipRunner{");
        sb.append("localFileNameRegex='").append(this.getLocalFileNameRegex()).append('\'');
        sb.append("zipFilePath='").append(this.getZipFilePath()).append('\'');
        sb.append("destLocalPath='").append(this.getDestLocalPath()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
