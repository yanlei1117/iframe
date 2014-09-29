package com.asiainfo.dbx.ln.business.runner.runner.file;

import com.asiainfo.dbx.ln.component.util.AppFileUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/23.
 */
public class FileListRunner extends  FileRunner {
    @Override
    public void run() throws Throwable {
        String varLocalPath = (String)AppVarUtils.getVarContainer(FileListRunner.class).getVar(this.getLocalPath());
        String varlocalFileNameRegex = (String)AppVarUtils.getVarContainer(FileListRunner.class).getVar(this.getLocalFileNameRegex());
        Object obj = AppFileUtils.getFileService(FileListRunner.class).listFile(varLocalPath,varlocalFileNameRegex);
        if(this.getDefineName() != null){
            AppVarUtils.getVarContainer(FileListRunner.class).setVar(this.getDefineName(),obj);
        }
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nFileListRunner{");
        sb.append("localPath='").append(this.getLocalPath()).append('\'');
        sb.append("localFileNameRegex='").append(this.getLocalFileNameRegex()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
