package com.asiainfo.dbx.ln.business.service.operation;

import com.asiainfo.dbx.ln.business.service.ServiceConstant;

/**
 * Created by yanlei on 2014/9/3.
 */
public class OperationDefineFactory {
    public static OperationDefine createOperationDefine(String operationName){
        return new DefaultOperationDefine(operationName);
    }
    private static OperationDefine operationDefineForCreate = createOperationDefine(ServiceConstant.RESOURCE_OPERATOR_TYPE_CREATE);
    public static OperationDefine getOperationDefineForCreate(){
        return operationDefineForCreate;
    }

    private static OperationDefine operationDefineForUpdate = createOperationDefine(ServiceConstant.RESOURCE_OPERATOR_TYPE_UPDATE);

    public static OperationDefine getOperationDefineForUpdate(){
        return operationDefineForUpdate;
    }

    private static OperationDefine operationDefineForDelete = createOperationDefine(ServiceConstant.RESOURCE_OPERATOR_TYPE_DELETE);
    public static OperationDefine getOperationDefineForDelete(){
        return operationDefineForDelete;
    }

    private static OperationDefine operationDefineForRead = createOperationDefine(ServiceConstant.RESOURCE_OPERATOR_TYPE_READ);


    public static OperationDefine getOperationDefineForRead(){
        return operationDefineForRead;
    }

    private static OperationDefine operationDefineForCount = createOperationDefine(ServiceConstant.RESOURCE_OPERATOR_TYPE_COUNT);
    public static OperationDefine getOperationDefineForCount(){
        return operationDefineForCount;
    }

}
