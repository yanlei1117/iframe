package com.asiainfo.dbx.ln.business.transport.http.authorization;

import com.asiainfo.dbx.ln.component.api.Loader;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;

import com.asiainfo.dbx.ln.component.util.AppSpringUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Created by yanlei on 2014/9/25.
 */
public class  DefaultAuthorizationValidator  implements  AuthorizationValidator,Loader {

    private Collection<AuthorizationValidator> authorizationValidatorCollection = null;

    @Override
    public void load() throws Exception {
        Map<String,AuthorizationValidator> map =  AppSpringUtils.getApplicationContextHolder().getBeansOfType(AuthorizationValidator.class);
        if(map != null && map.size()>0){
            authorizationValidatorCollection = new ArrayList(map.size());
            for(AuthorizationValidator authorizationValidator:map.values()){
                if(authorizationValidator != this){
                    authorizationValidatorCollection.add(authorizationValidator);
                }
            }
        }else{
            authorizationValidatorCollection = Collections.EMPTY_LIST;
        }
    }

    public boolean isAuthorization(String domain, ResourceDefine resourceDefine, OperationDefine operationDefine, String requestBody, ServletWebRequest servletWebRequest){
        if(authorizationValidatorCollection != null){
            for(AuthorizationValidator authorizationValidator:authorizationValidatorCollection){
                boolean authFlag = authorizationValidator.isAuthorization(domain,resourceDefine,operationDefine,requestBody,servletWebRequest);
                if(!authFlag){
                    return false;
                }
            }
        }
        return true;
    }
}
