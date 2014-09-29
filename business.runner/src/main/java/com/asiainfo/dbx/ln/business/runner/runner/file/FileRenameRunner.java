package com.asiainfo.dbx.ln.business.runner.runner.file;

import com.asiainfo.dbx.ln.component.util.AppFileUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/23.
 */
public class FileRenameRunner extends  FileRunner {
    @Override
    public void run() throws Throwable {
        String varLocalPath = (String)AppVarUtils.getVarContainer(FileRenameRunner.class).getVar(this.getLocalPath());
        String varLocalFileNameRegex = (String)AppVarUtils.getVarContainer(FileRenameRunner.class).getVar(this.getLocalFileNameRegex());
        AppFileUtils.getFileService(FileRenameRunner.class).renameFile(varLocalPath,varLocalFileNameRegex,this.getDestFileNameRegex());

    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nFileRenameRunner{");
        sb.append("localPath='").append(this.getLocalPath()).append('\'');
        sb.append("localFileNameRegex='").append(this.getLocalFileNameRegex()).append('\'');
        sb.append("destFileNameRegex='").append(this.getDestFileNameRegex()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
