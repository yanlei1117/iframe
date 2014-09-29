package com.asiainfo.dbx.ln.business.runner.runner.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.component.util.AppFtpUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpListFileRunner extends FtpRunner{


    @Override
    public void run() throws Throwable {
        String varServerPath = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getServerPath());
        String varServerFileNameRegex = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getServerFileNameRegex());
        Object result = AppFtpUtils.getFtpOperatorService(FtpDeleteAllFileRunner.class).getFileListInDir(this.getServerId(),varServerPath,varServerFileNameRegex);
        if(this.getDefineName() != null){
            AppVarUtils.getVarContainer(FtpDeleteAllFileRunner.class).setVar(this.getDefineName(),result);
        }
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nFtpListFileRunner{");
        sb.append("serverId='").append(this.getServerId()).append('\'');
        sb.append(",serverPath='").append(this.getServerPath()).append('\'');
        sb.append(",serverFileNameRegex ='").append(this.getServerFileNameRegex()).append('\'');
        sb.append(",desc ='").append(this.getDesc()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
