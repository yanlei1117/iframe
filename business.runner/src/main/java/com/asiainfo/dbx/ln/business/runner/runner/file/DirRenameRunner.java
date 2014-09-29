package com.asiainfo.dbx.ln.business.runner.runner.file;

import com.asiainfo.dbx.ln.component.util.AppFileUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/23.
 */
public class DirRenameRunner extends  FileRunner {
    @Override
    public void run() throws Throwable {
        String varLocalPath = (String)AppVarUtils.getVarContainer(DirRenameRunner.class).getVar(this.getLocalPath());
        String varDestDirName = (String)AppVarUtils.getVarContainer(DirRenameRunner.class).getVar(this.getDestDirName());
        AppFileUtils.getFileService(DirRenameRunner.class).renameDir(varLocalPath, varDestDirName);

    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nDirRenameRunner{");
        sb.append("localPath='").append(this.getLocalPath()).append('\'');
        sb.append("destDirName='").append(this.getDestDirName()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
