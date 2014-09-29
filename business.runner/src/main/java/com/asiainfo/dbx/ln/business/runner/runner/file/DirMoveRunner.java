package com.asiainfo.dbx.ln.business.runner.runner.file;

import com.asiainfo.dbx.ln.component.util.AppFileUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/23.
 */
public class DirMoveRunner extends  FileRunner {
    @Override
    public void run() throws Throwable {
        String varLocalPath = (String)AppVarUtils.getVarContainer(DirMoveRunner.class).getVar(this.getLocalPath());

        String varDestLocalPath = (String)AppVarUtils.getVarContainer(DirMoveRunner.class).getVar(this.getDestLocalPath());
        AppFileUtils.getFileService(DirMoveRunner.class).moveDir(varLocalPath, varDestLocalPath);

    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nDirMoveRunner{");
        sb.append("localPath='").append(this.getLocalPath()).append('\'');
        sb.append("destLocalPath='").append(this.getDestLocalPath()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
