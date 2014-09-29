package com.asiainfo.dbx.ln.business.service.resource;

/**
 * Created by yanlei on 2014/9/3.
 */
public interface ResourceDefine {

    public static final String RESOURCE_TYPE_DATA = "data";
    public static final String RESOURCE_TYPE_PROCEDURE = "procedure";
   // public static final String RESOURCE_TYPE_FTP = "ftp";
    public String getResourceType();
 //   public boolean isValid() ;
    public boolean matchResource(ResourceDefine resourceDefine);
}
