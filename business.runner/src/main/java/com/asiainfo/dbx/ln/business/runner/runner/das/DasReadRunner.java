package com.asiainfo.dbx.ln.business.runner.runner.das;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefineFactory;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefineFactory;
import com.asiainfo.dbx.ln.business.util.AppServiceSupportUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/19.
 */
public class DasReadRunner extends DasRunner {


    String itemId;
    String criteria;
    String defineName;
    public String getDefineName() {
        return defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }
    public String getItemId()  throws Exception{
        if(itemId == null){
            return itemId;
        }else {
            return AppVarUtils.getVarContainer(DasUpdateRunner.class).getVar(itemId).toString();
        }
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    /**
     * @throws Exception
     */
    @Override
    public void run() throws Throwable {
        DataResourceDefine resourceDefine = null;

        if(AppValidationUtils.isEmpty(itemId)){
            resourceDefine = ResourceDefineFactory.getDataResourceDefine(this.getRepository(), this.getContainer(), this.getCollection());
        }else{
             resourceDefine = ResourceDefineFactory.getDataResourceDefine(this.getRepository(), this.getContainer(), this.getCollection());
            resourceDefine.setItemId(this.getItemId());


        }
        OperationDefine operationDefine = OperationDefineFactory.getOperationDefineForRead();
        Object result = AppServiceSupportUtils.getServiceSupport(DasReadRunner.class).call(resourceDefine,operationDefine,this.getCriteria());
        AppVarUtils.getVarContainer(DasCreateRunner.class).setVar(this.getDefineName(),result);
    }
    @Override
    public String toString() {
        return "\nDasReadRunner{" +
                "repository='" + this.getRepository() + '\'' +
                ", container='" + this.getContainer() + '\'' +
                ", getCollection='" + this.getCollection() + '\'' +
                ", itemId='" + itemId + '\'' +
                ", criteria='" + criteria + '\'' +
                ", defineName='" + defineName + '\'' +
                '}';
    }

}
