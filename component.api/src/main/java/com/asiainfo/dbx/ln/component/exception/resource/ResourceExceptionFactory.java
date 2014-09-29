package com.asiainfo.dbx.ln.component.exception.resource;

/**
 * Created by yanlei on 2014/9/22.
 */
public class ResourceExceptionFactory {

    public static InvalidDataResourceException createInvalidDataResourceException(String repository, String container, String collection){
        return new InvalidDataResourceException(repository,container,collection);
    }

    public static NoDataResourceException createNoDataResourceException(String repository, String container, String collection){
        return new NoDataResourceException(repository,container,collection);
    }


    public static DuplicateDataResourceException createDuplicateDataResourceException(String repository,String container, String collection){
        return new DuplicateDataResourceException(repository,container,collection);
    }


    public static InvalidProcedureResourceException createInvalidProcedureResourceException(String repository,String container, String procedureName){
        return new InvalidProcedureResourceException(repository,container,procedureName);
    }

    public static DuplicateProcedureResourceException createDuplicateProcedureResourceException(String repository,String container, String procedureName){
        return new DuplicateProcedureResourceException(repository,container,procedureName);
    }

    public static NoProcedureResourceException createNoProcedureResourceException(String repository,String container, String procedureName){
        return new NoProcedureResourceException(repository,container,procedureName);
    }




}
