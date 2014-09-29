package com.asiainfo.dbx.ln.business.runner.runner.file;

import com.asiainfo.dbx.ln.component.util.AppFileUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/23.
 */
public class DirDeleteRunner extends  FileRunner {

    @Override
    public void run() throws Throwable {
        String varLocalPath = (String)AppVarUtils.getVarContainer(DirDeleteRunner.class).getVar(this.getLocalPath());

        AppFileUtils.getFileService(DirDeleteRunner.class).deleteDir(varLocalPath);

    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nDirDeleteRunner{");
        sb.append("localPath='").append(this.getLocalPath()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
