package com.asiainfo.dbx.ln.component.util;

import com.asiainfo.dbx.ln.component.spring.ApplicationContextHolder;
import com.asiainfo.dbx.ln.component.spring.impl.ApplicationContextHolderParentFixed;

/**
 * Created by yanlei on 2014/8/26.
 */
public class AppSpringUtils {
    private static ApplicationContextHolder applicationContextHolder = null;

    public static ApplicationContextHolder getApplicationContextHolder() {
        return applicationContextHolder;
    }

    public static void setApplicationContextHolder(ApplicationContextHolder applicationContextHolder) {
        AppSpringUtils.applicationContextHolder = applicationContextHolder;
    }
    public static  void initDefaultApplicationContextHolder() throws Exception{
        setApplicationContextHolder(new ApplicationContextHolderParentFixed());
    }

}
