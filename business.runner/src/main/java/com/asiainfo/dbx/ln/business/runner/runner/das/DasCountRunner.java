package com.asiainfo.dbx.ln.business.runner.runner.das;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefineFactory;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefineFactory;
import com.asiainfo.dbx.ln.business.util.AppServiceSupportUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/19.
 */
public class DasCountRunner extends DasRunner {



    String criteria;
    String defineName;


    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getDefineName() {
        return defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    /**
     * @throws Exception
     */
    @Override
    public void run() throws Throwable {
        ResourceDefine   resourceDefine = ResourceDefineFactory.createDataResourceDefine(this.getRepository(), this.getContainer(), this.getCollection());
        OperationDefine operationDefine = OperationDefineFactory.getOperationDefineForCount();
        Object result = AppServiceSupportUtils.getServiceSupport(DasCountRunner.class).call(resourceDefine,operationDefine,this.getCriteria());
        AppVarUtils.getVarContainer(DasCreateRunner.class).setVar(this.getDefineName(),result);
    }

    @Override
    public String toString() {
        return "\nDasCountRunner{" +
                "repository='" + this.getRepository() + '\'' +
                ", container='" + this.getContainer() + '\'' +
                ", getCollection='" + this.getCollection() + '\'' +
                ", criteria='" + criteria + '\'' +
                ", defineName='" + defineName + '\'' +
                '}';
    }
}
