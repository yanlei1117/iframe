package com.asiainfo.dbx.ln.business.service.operation;

/**
 * Created by yanlei on 2014/9/3.
 */
public class DefaultOperationDefine implements OperationDefine {
    private String operationName;

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public DefaultOperationDefine(String operationName) {
        this.operationName = operationName;
    }
}
