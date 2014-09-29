package com.asiainfo.dbx.ln.business.runner.runner.das;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefineFactory;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefineFactory;
import com.asiainfo.dbx.ln.business.util.AppServiceSupportUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

import java.util.Map;

/**
 * Created by yanlei on 2014/9/19.
 */
public class DasDeleteRunner extends DasRunner {

    String itemId;

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

    /**
     * @throws Exception
     */
    @Override
    public void run() throws Throwable {
        DataResourceDefine resourceDefine = ResourceDefineFactory.getDataResourceDefine(this.getRepository(), this.getContainer(), this.getCollection());
        resourceDefine.setItemId(this.getItemId());
        OperationDefine operationDefine = OperationDefineFactory.getOperationDefineForCreate();
        Object result = AppServiceSupportUtils.getServiceSupport(DasCreateRunner.class).call(resourceDefine,operationDefine,(Map)null);
    }
    @Override
    public String toString() {
        return "\nDasDeleteRunner{" +
                "repository='" + this.getRepository() + '\'' +
                ", container='" + this.getContainer() + '\'' +
                ", getCollection='" + this.getCollection() + '\'' +
                ", itemId='" + itemId + '\'' +
                '}';
    }


}
