package com.asiainfo.dbx.ln.business.service.data.result;

/**
 * Created by yanlei on 2014/9/13.
 */
public class DataEffectResult {
    public DataEffectResult(int effectRecordNum) {
        this.effectRecordNum = effectRecordNum;
    }

    int effectRecordNum = 0;

    public int getEffectRecordNum() {
        return effectRecordNum;
    }

    public void setEffectRecordNum(int effectRecordNum) {
        this.effectRecordNum = effectRecordNum;
    }

    public Object effectModel;

    public Object getEffectModel() {
        return effectModel;
    }

    public void setEffectModel(Object effectModel) {
        this.effectModel = effectModel;
    }
}
