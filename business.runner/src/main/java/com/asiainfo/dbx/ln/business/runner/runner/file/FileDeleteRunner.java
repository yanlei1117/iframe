package com.asiainfo.dbx.ln.business.runner.runner.file;

import com.asiainfo.dbx.ln.component.util.AppFileUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/23.
 */
public class FileDeleteRunner extends  FileRunner {
    @Override
    public void run() throws Throwable {
        String varLocalPath = (String)AppVarUtils.getVarContainer(FileDeleteRunner.class).getVar(this.getLocalPath());
        String varlocalFileNameRegex = (String)AppVarUtils.getVarContainer(FileDeleteRunner.class).getVar(this.getLocalFileNameRegex());
          AppFileUtils.getFileService(FileDeleteRunner.class).deleteFile(varLocalPath,varlocalFileNameRegex);

    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nFileDeleteRunner{");
        sb.append("localPath='").append(this.getLocalPath()).append('\'');
        sb.append("localFileNameRegex='").append(this.getLocalFileNameRegex()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
