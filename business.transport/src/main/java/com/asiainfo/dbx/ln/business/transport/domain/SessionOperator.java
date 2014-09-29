package com.asiainfo.dbx.ln.business.transport.domain;

import java.util.Map;

/**
 * Created by yanlei on 2014/9/5.
 */
public interface SessionOperator {
    public Map getSession() throws  Exception;
    public String startSession() throws  Exception;
    public void endSession() throws  Exception;
    public boolean isInSession() throws  Exception;
}
