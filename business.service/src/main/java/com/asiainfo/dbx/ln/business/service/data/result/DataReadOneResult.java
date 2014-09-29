package com.asiainfo.dbx.ln.business.service.data.result;

/**
 * Created by yanlei on 2014/9/13.
 */
public class DataReadOneResult {
    int readNum =0;
    Object dataModel;

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public Object getDataModel() {
        return dataModel;
    }

    public void setDataModel(Object dataModel) {
        this.dataModel = dataModel;
    }
}
