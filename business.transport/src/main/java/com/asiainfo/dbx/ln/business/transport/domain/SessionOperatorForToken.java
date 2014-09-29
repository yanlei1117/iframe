package com.asiainfo.dbx.ln.business.transport.domain;

import com.asiainfo.dbx.ln.business.service.ServiceConstant;
import com.asiainfo.dbx.ln.component.util.AppStringUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import com.asiainfo.dbx.ln.component.var.VarConstant;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanlei on 2014/9/5.
 */
public class SessionOperatorForToken implements SessionOperator{
    @Override
    public Map getSession() throws  Exception{
        String token = getToken();
        if(token != null){
            return (Map)AppVarUtils.getVarContainer(SessionOperatorForToken.class).getVar(VarConstant.VAR_NAMED_SESSION + "." + token);
        }
        return null;
    }

    @Override
    public String startSession() throws  Exception{
        String token = createToken();
        AppVarUtils.getVarContainer(SessionOperatorForToken.class).setVar(VarConstant.VAR_NAMED_SESSION + "." + token, new HashMap());
        return token;
    }

    @Override
    public void endSession() throws  Exception{
        String token = getToken();
        if(token != null){
            AppVarUtils.getVarContainer(SessionOperatorForToken.class).setVar(VarConstant.VAR_NAMED_SESSION + "." + token,null);
        }
    }
    public boolean isInSession()throws  Exception{
        return getSession() != null;
    }

    private String createToken(){
        return AppStringUtils.getUUID();
    }

    private String getToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(ServiceConstant.REQUEST_HEADER_TOKEN_NAME);
        return token;
    }
}
