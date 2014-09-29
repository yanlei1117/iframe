package com.asiainfo.dbx.ln.business.runner.runner.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.component.util.AppFtpUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpRenameAllFileRunner extends FtpRunner{

    String srcFileNameRegex;
    String destFileNameRegex;

    public String getSrcFileNameRegex() {
        return srcFileNameRegex;
    }

    public void setSrcFileNameRegex(String srcFileNameRegex) {
        this.srcFileNameRegex = srcFileNameRegex;
    }

    public String getDestFileNameRegex() {
        return destFileNameRegex;
    }

    public void setDestFileNameRegex(String destFileNameRegex) {
        this.destFileNameRegex = destFileNameRegex;
    }

    @Override
    public void run() throws Throwable {
        String varServerPath = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getServerPath());
        String varSrcFileNameRegex = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getSrcFileNameRegex());

        String varDestFileNameRegex = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getDestFileNameRegex());

        Object result = AppFtpUtils.getFtpOperatorService(FtpDeleteAllFileRunner.class).renameAllServerFile(this.getServerId(),varServerPath,varSrcFileNameRegex,varDestFileNameRegex,false);
        if(this.getDefineName() != null){
            AppVarUtils.getVarContainer(FtpDeleteAllFileRunner.class).setVar(this.getDefineName(),result);
        }
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nFtpRenameAllFileRunner{");
        sb.append("serverId='").append(this.getServerId()).append('\'');
        sb.append(",serverPath='").append(this.getServerPath()).append('\'');
        sb.append(",srcFileNameRegex='").append(this.getLocalPath()).append('\'');
        sb.append(",destFileNameRegex='").append(this.getLocalFileName()).append('\'');

        sb.append(",desc ='").append(this.getDesc()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
