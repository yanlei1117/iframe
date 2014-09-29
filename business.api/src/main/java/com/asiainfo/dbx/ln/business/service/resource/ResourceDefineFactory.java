package com.asiainfo.dbx.ln.business.service.resource;

import com.asiainfo.dbx.ln.component.exception.resource.NoDataResourceException;
import com.asiainfo.dbx.ln.component.exception.resource.NoProcedureResourceException;
import com.asiainfo.dbx.ln.component.exception.resource.ResourceExceptionFactory;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yanlei on 2014/9/3.
 */
public class ResourceDefineFactory {


    private static  Map<String,ProcedureResourceDefine> procedureResourceMap = new ConcurrentHashMap<String, ProcedureResourceDefine>();
    public static ProcedureResourceDefine createProcedureResourceDefine(String repository, String container,String procedureName){
        String key = (repository+"/"+container+"/"+procedureName).toLowerCase();
        if(!procedureResourceMap.containsKey(key)){
            if(AppValidationUtils.isEmpty(repository) || AppValidationUtils.isEmpty(container) || AppValidationUtils.isEmpty(procedureName) ){
                throw ResourceExceptionFactory.createInvalidProcedureResourceException(repository, container, procedureName);
            }
            ProcedureResourceDefine procedureResourceDefine =  new ProcedureResourceDefine(repository,container,procedureName);
            procedureResourceMap.put(key,procedureResourceDefine);
            return procedureResourceDefine;
        }else{
            throw ResourceExceptionFactory.createDuplicateProcedureResourceException(repository,container,procedureName);
        }
    }


    public static ProcedureResourceDefine getProcedureResourceDefine(String repository, String container,String procedureName)  throws NoProcedureResourceException {
        String key = (repository+"/"+container+"/"+procedureName).toLowerCase();
        if(procedureResourceMap.containsKey(key)){
            return procedureResourceMap.get(key);
        }else{
          throw ResourceExceptionFactory.createNoProcedureResourceException(repository, container, procedureName);
        }
    }




    private static  Map<String,DataResourceDefine> dataResourceMap = new ConcurrentHashMap<String, DataResourceDefine>();

    public static DataResourceDefine createDataResourceDefine(   String repository, String container,String collection ){
        String key=(repository+"/"+container+"/"+collection).toLowerCase();
        if(!dataResourceMap.containsKey(key)){
            if (AppValidationUtils.isEmpty(repository) || AppValidationUtils.isEmpty(container) || AppValidationUtils.isEmpty(collection)) {
                throw ResourceExceptionFactory.createInvalidDataResourceException(repository, container, collection);
            }
            DataResourceDefine dataResourceDefine  = new DataResourceDefine(repository, container, collection);
            dataResourceMap.put(key,dataResourceDefine);
            return dataResourceDefine;
        }else{
            throw ResourceExceptionFactory.createDuplicateDataResourceException(repository, container, collection);
        }

    }
    public static DataResourceDefine getDataResourceDefine(String repository, String container,String collection)  throws NoDataResourceException {
        String key = (repository+"/"+container+"/"+collection).toLowerCase();
        if(dataResourceMap.containsKey(key)){
            return dataResourceMap.get(key);
        }else{
            throw ResourceExceptionFactory.createNoDataResourceException(repository, container, collection);
        }
    }



}
