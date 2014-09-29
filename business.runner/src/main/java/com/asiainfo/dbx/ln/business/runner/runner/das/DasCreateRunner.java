package com.asiainfo.dbx.ln.business.runner.runner.das;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefineFactory;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefineFactory;
import com.asiainfo.dbx.ln.business.util.AppServiceSupportUtils;

/**
 * Created by yanlei on 2014/9/19.
 */
public class DasCreateRunner  extends DasRunner {

    String item;


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }



    /**
     * @throws Exception
     */
    @Override
    public void run() throws Throwable {
        ResourceDefine resourceDefine = ResourceDefineFactory.createDataResourceDefine(this.getRepository(),this.getContainer(),this.getCollection());
        OperationDefine operationDefine = OperationDefineFactory.getOperationDefineForCreate();
        Object result = AppServiceSupportUtils.getServiceSupport(DasCreateRunner.class).call(resourceDefine,operationDefine,this.getItem());

    }
    @Override
    public String toString() {
        return "\nDasCreateRunner{" +
                "repository='" + this.getRepository() + '\'' +
                ", container='" + this.getContainer() + '\'' +
                ", getCollection='" + this.getCollection() + '\'' +
                ", item='" + item + '\'' +
                '}';
    }

}
