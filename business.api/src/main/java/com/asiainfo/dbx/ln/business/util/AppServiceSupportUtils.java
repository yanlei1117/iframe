package com.asiainfo.dbx.ln.business.util;

import com.asiainfo.dbx.ln.business.service.ServiceSupport;
import com.asiainfo.dbx.ln.component.util.AppJsonUtils;
import com.asiainfo.dbx.ln.component.util.AppSpringUtils;

/**
 * Created by yanlei on 2014/9/16.
 */
public class AppServiceSupportUtils {
    private volatile  static ServiceSupport serviceSupport;
    public static ServiceSupport getServiceSupport(Class classz) throws Exception{
        if(AppServiceSupportUtils.serviceSupport == null){
            synchronized (AppJsonUtils.class){
                if(AppServiceSupportUtils.serviceSupport ==null) {
                    ServiceSupport serviceSupport1 = (ServiceSupport) AppSpringUtils.getApplicationContextHolder().getBean("defaultServiceSupport");
                    if (serviceSupport1 == null) {
                        throw new Exception("no bean named 'defaultServiceSupport'");
                    }
                    AppServiceSupportUtils.serviceSupport = serviceSupport1;
                }
            }
        }
        return AppServiceSupportUtils.serviceSupport;
    }
}
