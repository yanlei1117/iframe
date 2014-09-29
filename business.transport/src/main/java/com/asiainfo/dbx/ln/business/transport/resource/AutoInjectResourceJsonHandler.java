package com.asiainfo.dbx.ln.business.transport.resource;

/**
 * Created by yanlei on 2014/9/25.
 */
public abstract   class AutoInjectResourceJsonHandler implements ResourceJsonHandler {
    ResourceJsonHandler  resourceJsonHandler;

    public ResourceJsonHandler getResourceJsonHandler() {
        return resourceJsonHandler;
    }

    public void setResourceJsonHandler(ResourceJsonHandler resourceJsonHandler) {
        this.resourceJsonHandler = resourceJsonHandler;
    }
}
