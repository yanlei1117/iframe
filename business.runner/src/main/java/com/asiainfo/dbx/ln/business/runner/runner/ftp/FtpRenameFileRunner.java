package com.asiainfo.dbx.ln.business.runner.runner.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.component.util.AppFtpUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpRenameFileRunner extends FtpRunner{

    String srcFileName;
    String destFileName;

    public String getSrcFileName() {
        return srcFileName;
    }

    public void setSrcFileName(String srcFileName) {
        this.srcFileName = srcFileName;
    }

    public String getDestFileName() {
        return destFileName;
    }

    public void setDestFileName(String destFileName) {
        this.destFileName = destFileName;
    }

    @Override
    public void run() throws Throwable {
        String varServerPath = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(serverPath);
        String varSrcFileName  = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getSrcFileName());
        String varDestFileName  = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getDestFileName());
        Object result = AppFtpUtils.getFtpOperatorService(FtpDeleteAllFileRunner.class).renameServerFile(this.getServerId(),varServerPath,varSrcFileName,varDestFileName);
        if(this.getDefineName() != null){
            AppVarUtils.getVarContainer(FtpDeleteAllFileRunner.class).setVar(this.getDefineName(),result);
        }
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nFtpRenameFileRunner{");
        sb.append("serverId='").append(this.getServerId()).append('\'');
        sb.append(",serverPath='").append(this.getServerPath()).append('\'');
        sb.append(",srcFileName='").append(this.getSrcFileName()).append('\'');
        sb.append(",destFileName='").append(this.getDestFileName()).append('\'');
        sb.append(",desc ='").append(this.getDesc()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
