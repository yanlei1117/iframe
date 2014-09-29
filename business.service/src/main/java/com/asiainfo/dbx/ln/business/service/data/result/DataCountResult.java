package com.asiainfo.dbx.ln.business.service.data.result;

/**
 * Created by yanlei on 2014/9/13.
 */
public class DataCountResult {
    public DataCountResult(float countResult){
        this.countResult = countResult;
    }
    private float countResult;

    public float getCountResult() {
        return countResult;
    }

    public void setCountResult(float countResult) {
        this.countResult = countResult;
    }
}
