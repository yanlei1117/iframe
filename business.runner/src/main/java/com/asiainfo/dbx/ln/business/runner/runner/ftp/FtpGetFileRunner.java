package com.asiainfo.dbx.ln.business.runner.runner.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.component.util.AppFtpUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpGetFileRunner extends FtpRunner{

    @Override
    public void run() throws Throwable {
        String varServerPath = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getServerPath());
        String varServerFileName = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getServerFileName());
        String varLocalPath = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getLocalPath());
        String varLocalFileName = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getLocalFileName());
        Object result = AppFtpUtils.getFtpOperatorService(FtpDeleteAllFileRunner.class).downloadFile(this.getServerId(),varServerPath,varServerFileName,varLocalPath,varLocalFileName);
        if(this.getDefineName() != null){
            AppVarUtils.getVarContainer(FtpDeleteAllFileRunner.class).setVar(this.getDefineName(),result);
        }
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nFtpGetFileRunner{");
        sb.append("serverId='").append(this.getServerId()).append('\'');
        sb.append(",serverPath='").append(this.getServerPath()).append('\'');
        sb.append(",serverFileName ='").append(this.getServerFileName()).append('\'');
        sb.append(",localPath='").append(this.getLocalPath()).append('\'');
        sb.append(",localFileName='").append(this.getLocalFileName()).append('\'');
        sb.append(",desc ='").append(this.getDesc()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
