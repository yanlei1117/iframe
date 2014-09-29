package com.asiainfo.dbx.ln.business.runner.runner.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.component.util.AppFtpUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpMoveFileRunner extends FtpRunner{



    @Override
    public void run() throws Throwable {
        String varSrcServerPath = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getSrcServerPath());
        String varSrcServerFileName = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getSrcServerFileName());
        String varDestServerPath = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getDestServerPath());
        String varDestServerFileName = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(this.getDestServerFileName());
        Object result = AppFtpUtils.getFtpOperatorService(FtpDeleteAllFileRunner.class).moveServerFile(this.getServerId(),varSrcServerPath,varSrcServerFileName,varDestServerPath,varDestServerFileName);
        if(this.getDefineName() != null){
            AppVarUtils.getVarContainer(FtpDeleteAllFileRunner.class).setVar(this.getDefineName(),result);
        }
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nFtpMoveFileRunner{");
        sb.append("serverId='").append(this.getServerId()).append('\'');
        sb.append(",srcServerPath='").append(this.getSrcServerPath()).append('\'');
        sb.append(",srcServerFileName='").append(this.getSrcServerFileName()).append('\'');
        sb.append(",destServerPath='").append(this.getDestServerPath()).append('\'');
        sb.append(",destServerFileName='").append(this.getDestServerFileName()).append('\'');
        sb.append(",desc ='").append(this.getDesc()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
