package com.asiainfo.dbx.ln.business.service.resource;

import com.asiainfo.dbx.ln.component.util.AppStringUtils;

/**
 * Created by yanlei on 2014/9/22.
 */
public class FtpResourceDefine implements ResourceDefine {
    String resourceType ="";
    String serverId;
    String serverPath;
    String serverFileName;

    public FtpResourceDefine(String serverId, String serverPath) {
        this.serverId = serverId;
        this.serverPath = serverPath;
    }

    public FtpResourceDefine(String serverId, String serverPath, String serverFileName) {
        this.serverId = serverId;
        this.serverPath = serverPath;
        this.serverFileName = serverFileName;
    }

    @Override
    public String getResourceType() {
        return this.resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FtpResourceDefine that = (FtpResourceDefine) o;

        if (resourceType != null ? !resourceType.equals(that.resourceType) : that.resourceType != null) return false;
        if (serverFileName != null ? !serverFileName.equals(that.serverFileName) : that.serverFileName != null)
            return false;
        if (serverId != null ? !serverId.equals(that.serverId) : that.serverId != null) return false;
        if (serverPath != null ? !serverPath.equals(that.serverPath) : that.serverPath != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = resourceType != null ? resourceType.hashCode() : 0;
        result = 31 * result + (serverId != null ? serverId.hashCode() : 0);
        result = 31 * result + (serverPath != null ? serverPath.hashCode() : 0);
        result = 31 * result + (serverFileName != null ? serverFileName.hashCode() : 0);
        return result;
    }

    @Override
    public boolean matchResource(ResourceDefine o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FtpResourceDefine that = (FtpResourceDefine) o;

        if (resourceType != null && !AppStringUtils.matchFullString(resourceType, that.resourceType)) return false;
        if (serverId != null && !AppStringUtils.matchFullString(serverId,that.serverId)) return false;
        if (serverPath != null && !AppStringUtils.matchFullString(serverPath,that.serverPath)) return false;
        if (serverFileName != null && !AppStringUtils.matchFullString(serverFileName,that.serverFileName)) return false;
        return true;
    }
}
