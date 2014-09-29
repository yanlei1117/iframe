package com.asiainfo.dbx.ln.business.runner.runner.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.component.util.AppFtpUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpPutAllFileRunner extends FtpRunner{

    @Override
    public void run() throws Throwable {
        String varServerPath = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getServerPath());
        String varLocalFileNameRegex = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getLocalFileNameRegex());
        String varLocalPath = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getLocalPath());
        Object result = AppFtpUtils.getFtpOperatorService(FtpDeleteAllFileRunner.class).uploadAllFile(this.getServerId(),varLocalPath,varLocalFileNameRegex,varServerPath,false);
        if(this.getDefineName() != null){
            AppVarUtils.getVarContainer(FtpDeleteAllFileRunner.class).setVar(this.getDefineName(),result);
        }
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nFtpPutAllFileRunner{");
        sb.append("serverId='").append(this.getServerId()).append('\'');
        sb.append(",serverPath='").append(this.getServerPath()).append('\'');
        sb.append(",localPath='").append(this.getLocalPath()).append('\'');
        sb.append(",destServerPath='").append(this.getLocalFileNameRegex()).append('\'');
        sb.append(",destServerFileName='").append(this.getDestServerFileName()).append('\'');
        sb.append(",desc ='").append(this.getDesc()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
