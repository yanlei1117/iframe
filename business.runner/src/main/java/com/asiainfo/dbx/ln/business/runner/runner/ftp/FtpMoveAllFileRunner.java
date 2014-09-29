package com.asiainfo.dbx.ln.business.runner.runner.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.component.util.AppFtpUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpMoveAllFileRunner extends FtpRunner{

    String srcServerFileNameRegex;

    public String getSrcServerFileNameRegex() {
        return srcServerFileNameRegex;
    }

    public void setSrcServerFileNameRegex(String srcServerFileNameRegex) {
        this.srcServerFileNameRegex = srcServerFileNameRegex;
    }

    @Override
    public void run() throws Throwable {
        String varSrcServerPath = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(srcServerPath);
        String varSrcServerFileNameRegex = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(srcServerFileNameRegex);
        String varDestServerPath = (String) AppVarUtils.getVarContainer(FtpGetAllFileRunner.class).getVar(destServerPath);

        Object result = AppFtpUtils.getFtpOperatorService(FtpDeleteAllFileRunner.class).moveAllServerFile(this.getServerId(),varSrcServerPath,varSrcServerFileNameRegex,varDestServerPath,false);
        if(this.getDefineName() != null){
            AppVarUtils.getVarContainer(FtpDeleteAllFileRunner.class).setVar(this.getDefineName(),result);
        }
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nFtpMoveAllFileRunner{");
        sb.append("serverId='").append(this.getServerId()).append('\'');
        sb.append(",serverPath='").append(this.getServerPath()).append('\'');
        sb.append(",srcServerFileNameRegex ='").append(this.getSrcServerFileNameRegex()).append('\'');
        sb.append(",destServerPath ='").append(this.getDestServerPath()).append('\'');
        sb.append(",desc ='").append(this.getDesc()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
