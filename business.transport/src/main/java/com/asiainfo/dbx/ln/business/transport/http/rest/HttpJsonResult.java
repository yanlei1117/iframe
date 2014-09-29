package com.asiainfo.dbx.ln.business.transport.http.rest;

/**
 * Created by yanlei on 2014/9/2.
 */
public class HttpJsonResult {
    int status =0;
    long failCode=0;
    Object result=null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getFailCode() {
        return failCode;
    }

    public void setFailCode(long failCode) {
        this.failCode = failCode;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
