package com.asiainfo.dbx.ln.business.transport.domain;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefineFactory;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.component.util.AppAssertUtils;
import com.asiainfo.dbx.ln.component.util.AppStringUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import com.asiainfo.dbx.ln.component.var.VarConstant;
import org.springframework.http.HttpEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by yanlei on 2014/9/2.
 */
public class DefaultBusinessDomain implements BusinessDomain {

    private String domainName= "";

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        AppAssertUtils.notNull(domainName,"domainName must not be null");
        this.domainName = domainName==null||domainName.trim().length()==0?null:domainName.trim();
    }

    Map<String,String> responseHeaderMap = null;

    public Map<String, String> getResponseHeaderMap() {
        return responseHeaderMap;
    }

    public void setResponseHeaderMap(Map<String, String> responseHeaderMap) {
        this.responseHeaderMap = responseHeaderMap;
    }

    @Override
    public boolean matchDomainName(String domainName){
        return AppStringUtils.matchFullString(this.domainName, domainName);

    } ;
    private String operationName="";

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    @Override
    public void renderResponseHeader( HttpEntity responseHttpEntity)   throws Exception{
        if(this.responseHeaderMap != null && responseHeaderMap.size()>0){
            Iterator<String> iterator = responseHeaderMap.keySet().iterator();
            while(iterator.hasNext()){
                String headerName = iterator.next();
                String headerValue = responseHeaderMap.get(headerName);
                if(headerValue != null && headerValue.trim().length()>0){
                    headerValue = (String) AppVarUtils.getVarContainer(DefaultBusinessDomain.class).getVar(headerName);
                }
                responseHttpEntity.getHeaders().add(headerName,headerValue);
            }
        }
    }

    @Override
    public OperationDefine findOperationDefine()  throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String opearation = null;
        if(this.getOperationName() != null) {
             opearation = request.getHeader(this.getOperationName());
            if (opearation == null) {
                opearation = request.getParameter(this.getOperationName());
            }
        }
        return opearation ==null? null: OperationDefineFactory.createOperationDefine(opearation);
    }

    @Override
    public void initRequest()  throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        AppVarUtils.getVarContainer(DefaultBusinessDomain.class).setVar(VarConstant.VAR_NAMED_REQUEST_PARAMS,request.getParameterMap());
        Map<String,String> headerMap = new HashMap<String,String>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while(enumeration.hasMoreElements()){
            String headerName = enumeration.nextElement();
            String headerValue = request.getHeader(headerName);
            headerMap.put(headerName,headerValue);
        }
        AppVarUtils.getVarContainer(DefaultBusinessDomain.class).setVar(VarConstant.VAR_NAMED_REQUEST_HEADERS,headerMap);
    }

    List<ResourceDefine> needLoginResourcsList = null;

    public List<ResourceDefine> getNeedLoginResourcsList() {
        return needLoginResourcsList;
    }

    public void setNeedLoginResourcsList(List<ResourceDefine> needLoginResourcsList) {
        this.needLoginResourcsList = needLoginResourcsList;
    }

    List<ResourceDefine > notNeedLoginResourcsList = null;

    public List<ResourceDefine> getNotNeedLoginResourcsList() {
        return notNeedLoginResourcsList;
    }

    public void setNotNeedLoginResourcsList(List<ResourceDefine> notNeedLoginResourcsList) {
        this.notNeedLoginResourcsList = notNeedLoginResourcsList;
    }

    SessionOperator sessionOperator;

    public SessionOperator getSessionOperator() {
        return sessionOperator;
    }

    public void setSessionOperator(SessionOperator sessionOperator) {
        this.sessionOperator = sessionOperator;
    }

    @Override
    public boolean isAuthorizationForResourceByLogin(ResourceDefine resourceDefine) throws Exception{
        if(this.sessionOperator != null ){
            if(this.sessionOperator.isInSession()){//logined
                return true;
            }
        }
        //not logined
        if(this.needLoginResourcsList!= null) {
            if(matchResources(this.notNeedLoginResourcsList,resourceDefine)){
                return false;
            }
            return true;

        }else if(this.notNeedLoginResourcsList != null){
            if(matchResources(this.notNeedLoginResourcsList,resourceDefine)){
                return true;
            }
            return false;

        }else{
            return true;// no resource  need login to visit
        }
    }
    private boolean matchResources(List<ResourceDefine> resourceDefineList,ResourceDefine targetResourceDefine){
        if(resourceDefineList != null) {
            Iterator<ResourceDefine> iterator = resourceDefineList.iterator();
            while (iterator.hasNext()) {
                ResourceDefine resourceDefine = iterator.next();
                if (resourceDefine.matchResource(targetResourceDefine)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DefaultBusinessDomain{");
        sb.append("notNeedLoginResourcsList=").append(notNeedLoginResourcsList);
        sb.append(", needLoginResourcsList=").append(needLoginResourcsList);
        sb.append(", operationName='").append(operationName).append('\'');
        sb.append(", responseHeaderMap=").append(responseHeaderMap);
        sb.append(", domainName='").append(domainName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
