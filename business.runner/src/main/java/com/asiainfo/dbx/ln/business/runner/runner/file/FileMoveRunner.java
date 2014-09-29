package com.asiainfo.dbx.ln.business.runner.runner.file;

import com.asiainfo.dbx.ln.component.util.AppFileUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/23.
 */
public class FileMoveRunner extends  FileRunner {
    @Override
    public void run() throws Throwable {
        String varLocalPath = (String)AppVarUtils.getVarContainer(FileMoveRunner.class).getVar(this.getLocalPath());
        String varLocalFileNameRegex = (String)AppVarUtils.getVarContainer(FileMoveRunner.class).getVar(this.getLocalFileNameRegex());
        String varDestLocalPath = (String)AppVarUtils.getVarContainer(FileMoveRunner.class).getVar(this.getDestLocalPath());
        AppFileUtils.getFileService(FileMoveRunner.class).moveFile(varLocalPath, varLocalFileNameRegex, varDestLocalPath);

    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nFileMoveRunner{");
        sb.append("localPath='").append(this.getLocalPath()).append('\'');
        sb.append("localFileNameRegex='").append(this.getLocalFileNameRegex()).append('\'');
        sb.append("destLocalPath='").append(this.getDestLocalPath()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
